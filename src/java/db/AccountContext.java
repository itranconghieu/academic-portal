// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import entity.Account;
import entity.Department;
import entity.Lecturer;
import entity.Major;
import entity.Student;
import entity.Permission;
import entity.Role;
import java.sql.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author End User
 */
public class AccountContext extends Open{
    public AccountContext () throws ClassNotFoundException, SQLException {
    }


    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    public String generateAuthCode() {
        StringBuilder authCode = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            char character = 0;
            while (!Character.isAlphabetic(character) && !Character.isDigit(character)) {
                character = (char) (Math.floor(Math.random() * 100));
            }
            authCode.append(character);
        }
        return authCode.toString();
    }

    public boolean removeAuthCode(String us, String authCode) throws SQLException {
        String sql = "UPDATE [dbo].[Authorize]\n"
                + "   SET [used] = 1\n"
                + " WHERE [for] = ? and authCode = ?";
        PreparedStatement p = cn.prepareStatement(sql);
        p.setString(1, us);
        p.setString(2, authCode);
        int affectedRow =  p.executeUpdate();
        return affectedRow > 0;
    }

    public String createAuthCode(String us) throws SQLException, ClassNotFoundException {
        String authCode = generateAuthCode();
        String s = "INSERT INTO [dbo].[Authorize]\n"
                + "           ([authCode]\n"
                + "           ,[for])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?)";
        PreparedStatement p = cn.prepareStatement(s);
        p.setString(1, authCode);
        p.setString(2, us);
        p.executeUpdate();
        return authCode;
    }

    public boolean verifyAuthCode(String us, String authCode) throws SQLException, ParseException {
        String s = "SELECT [authId]\n" +
"      ,[authCode]\n" +
"      ,[for]\n" +
"      ,[used]\n" +
"      ,[activeTime]\n" +
"  FROM [dbo].[Authorize]\n" +
"  where [for] = ? and authCode = ?;";
        PreparedStatement p = cn.prepareStatement(s);
        p.setString(1, us);
        p.setString(2, authCode);
        ResultSet rs = p.executeQuery();
        if (rs.next()) {
            Date activeTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").parse(rs.getString("activeTime"));
            return new Date().getTime() < activeTime.getTime() + 900000
                    && !Boolean.valueOf(rs.getBoolean("used"));

        }
        return false;
    }

    public Role getAccountRole(String us) throws SQLException, ParseException {
        String sql = "SELECT [username]\n"
                + "      ,[AccountRole].[roleId]\n"
                + "      ,[name]"
                + "      ,[addDate]\n"
                + "      ,[expireDate]\n"
                + "  FROM [dbo].[AccountRole]\n"
                + "JOIN Role\n"
                + "ON AccountRole.roleId = Role.roleId\n"
                + "WHERE username = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, us);
        Role role = new Role();
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            role.setId(rs.getInt("roleId"));
            role.setName(rs.getString("name"));
            role.setAddDate(df.parse(rs.getString("addDate")));
            try {
                role.setExpireDate(df.parse(rs.getString("expireDate")));
            } catch (NullPointerException e) {
                /**/
            }
        }
        sql = "SELECT RolePermission.permissionId, [name],  targetURL\n"
                + "FROM RolePermission\n"
                + "JOIN Permission\n"
                + "ON RolePermission.permissionId = Permission.permissionId\n"
                + "WHERE roleId = ?;";
        ps = cn.prepareStatement(sql);
        ps.setInt(1, role.getId());
        ArrayList<Permission> permissions = new ArrayList<>();
        rs = ps.executeQuery();
        while (rs.next()) {
            Permission permission = new Permission();
            permission.setId(rs.getInt("permissionId"));
            permission.setName(rs.getString("name"));
            permission.setTargetURL(rs.getString("targetURL"));
            permissions.add(permission);
        }
        role.setPermissions(permissions);
        return role;
    }


    public Account verifyAccount(String us, String pw) throws SQLException, ClassNotFoundException, ParseException {
        String sql = "SELECT [username]\n"
                + "      ,[password]\n"
                + "      ,[googleId]\n"
                + "  FROM [dbo].[Account]\n"
                + "WHERE username = ? AND password = ?";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, us);
        ps.setString(2, pw);
        Account ac = new Account();
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            ac = this.getInformation(us);
            ac.setPassword(pw);
            ac.setGoogleId(rs.getString("googleId"));
        }
        return ac;
    }

    public Account verifyGoogleAccountConnection(String googleId) throws SQLException, ParseException, ClassNotFoundException {
        String sql = "SELECT [username]\n"
                + "      ,[password]\n"
                + "      ,[googleId]\n"
                + "  FROM [dbo].[Account]\n"
                + "WHERE googleId =  ?";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, googleId);
        Account ac = new Account();
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            ac = this.getInformation(rs.getString("username"));
            ac.setPassword(rs.getString("password"));
            ac.setGoogleId(googleId);
        }
        return ac;
    }

    public Account getInformation(String us) throws SQLException, ParseException, ClassNotFoundException {
        Account ac = new Account();
        String sql = "SELECT [lecturerId]\n"
                + "      ,[departmentId]\n"
                + "      ,[surname]\n"
                + "      ,[middleName]\n"
                + "      ,[givenName]\n"
                + "      ,[dateOfBirth]\n"
                + "      ,[gender]\n"
                + "      ,[address]\n"
                + "      ,[imageURL]\n"
                + "      ,[email]\n"
                + "      ,[recognizeDate]\n"
                + "  FROM [dbo].[Lecturer]\n"
                + "  WHERE [lecturerId] = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, us);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Lecturer l = new Lecturer();
            l.setId(us);
            Department d = new DBContext()
                        .getDepartment(rs.getString("departmentId"));
                l.setDepartment(d);
                ac = l;
        } else {
            sql = "SELECT [studentId]\n"
                    + "      ,[majorId]\n"
                    + "      ,[surname]\n"
                    + "      ,[middleName]\n"
                    + "      ,[givenName]\n"
                    + "      ,[dateOfBirth]\n"
                    + "      ,[gender]\n"
                    + "      ,[address]\n"
                    + "      ,[imageURL]\n"
                    + "      ,[email]\n"
                    + "      ,[recognizeDate]\n"
                    + "  FROM [dbo].[Student]\n"
                    + "WHERE studentId = ?;";
            ps = cn.prepareStatement(sql);
            ps.setString(1, us);
            rs = ps.executeQuery();
            if (rs.next()) {
                Student s = new Student();
                s.setId(us);
                Major m = new DBContext().getMajor(rs.getString("majorId"));
                s.setMajor(m);
                ac = s;
            }
        }
        ac.setSurname(rs.getString("surname"));
        ac.setMiddleName(rs.getString("middleName"));
        ac.setGivenName(rs.getString("givenName"));
        ac.setDateOfBirth(df.parse(rs.getString("dateOfBirth")));
        ac.setGender(rs.getBoolean("gender"));
        ac.setAddress(rs.getString("address"));
        ac.setImageURL(rs.getString("imageURL"));
        ac.setEmail(rs.getString("email"));
        ac.setRecognizeDate(df.parse(rs.getString("recognizeDate")));
        return ac;
    }


    public boolean updatePassword(String us, String newpw) throws SQLException {
        String sql = "UPDATE [dbo].[Account]\n"
                + "   SET [password] = ?\n"
                + "WHERE username = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1, newpw);
        ps.setString(2, us);

        int affectedRow = ps.executeUpdate();
        return affectedRow > 0;
    }

    public boolean updateGoogleConnection(String us, String googleId) throws SQLException {
        String sql = "SELECT [googleId]"
                + "FROM [Account]"
                + "WHERE googleId = ?;";
        PreparedStatement ps = cn.prepareStatement(sql);
        ps.setString(1,  googleId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return false;
        } else {
             sql = "UPDATE [dbo].[Account]\n"
                + "   SET [googleId] = ?\n"
                + "WHERE username = ?;";
            ps = cn.prepareStatement(sql);
            ps.setString(1,  googleId);
            ps.setString(2, us);
            ps.executeUpdate();
            return true;
        }
    }
}
