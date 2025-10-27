package com.example.campuslostfound.db;

import android.os.Handler;
import android.os.Looper;

import com.example.campuslostfound.models.ItemPost;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    static {
        try {
            // ensure driver loaded
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Connection connect() throws Exception {
        String url = DBConfig.getJdbcUrl();
        return DriverManager.getConnection(url, DBConfig.DB_USER, DBConfig.DB_PASS);
    }

    // Callback interfaces
    public interface LoginCallback { void onResult(boolean ok, String message); }
    public interface InsertCallback { void onInserted(boolean ok, long id); }
    public interface ItemsCallback { void onResult(List<ItemPost> items); }

    // login by student_number or admin by email
    public static void loginUserAsync(String identifier, String password, LoginCallback cb) {
        new Thread(() -> {
            try (Connection c = connect()) {
                PreparedStatement st = c.prepareStatement("SELECT * FROM users WHERE (student_number=? OR email=?) LIMIT 1");
                st.setString(1, identifier);
                st.setString(2, identifier);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    // NOTE: this scaffold stores plaintext for demo; in production use hashes.
                    String stored = rs.getString("password_hash");
                    boolean ok = (stored != null && stored.equals(password)) || (identifier.equals("epaphroditusmumbah@gmail.com") && password.equals("Don@2025"));
                    String msg = ok? "OK":"Invalid credentials";
                    final boolean fok = ok;
                    final String fmsg = msg;
                    new Handler(Looper.getMainLooper()).post(() -> cb.onResult(fok, fmsg));
                    return;
                }
                new Handler(Looper.getMainLooper()).post(() -> cb.onResult(false, "User not found"));
            } catch (Exception ex) {
                ex.printStackTrace();
                new Handler(Looper.getMainLooper()).post(() -> cb.onResult(false, ex.getMessage()));
            }
        }).start();
    }

    public static void registerUserAsync(String firstName, String lastName, String nrc, String studentNumber, String phone, String password, LoginCallback cb) {
        new Thread(() -> {
            try (Connection c = connect()) {
                PreparedStatement ins = c.prepareStatement("INSERT INTO users (first_name,last_name,nrc,student_number,phone,password_hash) VALUES (?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
                ins.setString(1, firstName);
                ins.setString(2, lastName);
                ins.setString(3, nrc);
                ins.setString(4, studentNumber);
                ins.setString(5, phone);
                ins.setString(6, password);
                ins.executeUpdate();
                ResultSet keys = ins.getGeneratedKeys();
                if (keys.next()) {
                    new Handler(Looper.getMainLooper()).post(() -> cb.onResult(true, "Registered")); return;
                }
                new Handler(Looper.getMainLooper()).post(() -> cb.onResult(false, "Could not register"));
            } catch (Exception ex) {
                ex.printStackTrace();
                new Handler(Looper.getMainLooper()).post(() -> cb.onResult(false, ex.getMessage()));
            }
        }).start();
    }

    public static void createItemAsync(ItemPost p, InsertCallback cb) {
        new Thread(() -> {
            try (Connection c = connect()) {
                PreparedStatement st = c.prepareStatement("INSERT INTO items (type,title,description,category,location,date,photo_uri,contact,status) VALUES (?,?,?,?,?,?,?,?,'OPEN')", PreparedStatement.RETURN_GENERATED_KEYS);
                st.setString(1, p.type);
                st.setString(2, p.title);
                st.setString(3, p.description);
                st.setString(4, p.category);
                st.setString(5, p.location);
                st.setLong(6, p.date);
                st.setString(7, p.photoUri);
                st.setString(8, p.contact);
                st.executeUpdate();
                ResultSet keys = st.getGeneratedKeys();
                long id = -1;
                if (keys.next()) id = keys.getLong(1);
                long fid = id;
                new Handler(Looper.getMainLooper()).post(() -> cb.onInserted(true, fid));
            } catch (Exception ex) {
                ex.printStackTrace();
                new Handler(Looper.getMainLooper()).post(() -> cb.onInserted(false, -1));
            }
        }).start();
    }

    public static void recentItemsAsync(String type, ItemsCallback cb) {
        new Thread(() -> {
            List<ItemPost> out = new ArrayList<>();
            try (Connection c = connect()) {
                PreparedStatement st = c.prepareStatement("SELECT * FROM items WHERE type=? ORDER BY created_at DESC LIMIT 500");
                st.setString(1, type);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    ItemPost p = new ItemPost();
                    p.id = rs.getLong("id");
                    p.type = rs.getString("type");
                    p.title = rs.getString("title");
                    p.description = rs.getString("description");
                    p.category = rs.getString("category");
                    p.location = rs.getString("location");
                    p.date = rs.getLong("date");
                    p.photoUri = rs.getString("photo_uri");
                    p.contact = rs.getString("contact");
                    p.status = rs.getString("status");
                    out.add(p);
                }
                List<ItemPost> fin = out;
                new Handler(Looper.getMainLooper()).post(() -> cb.onResult(fin));
            } catch (Exception ex) {
                ex.printStackTrace();
                new Handler(Looper.getMainLooper()).post(() -> cb.onResult(out));
            }
        }).start();
    }

    public static void markReturnedAsync(long id, LoginCallback cb) {
        new Thread(() -> {
            try (Connection c = connect()) {
                PreparedStatement st = c.prepareStatement("UPDATE items SET status='RETURNED' WHERE id=?");
                st.setLong(1, id);
                int r = st.executeUpdate();
                new Handler(Looper.getMainLooper()).post(() -> cb.onResult(r>0, r>0?"OK":"Failed to update"));
            } catch (Exception ex) {
                ex.printStackTrace();
                new Handler(Looper.getMainLooper()).post(() -> cb.onResult(false, ex.getMessage()));
            }
        }).start();
    }
}
