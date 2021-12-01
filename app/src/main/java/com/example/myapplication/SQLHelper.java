package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Locale;

public class SQLHelper extends SQLiteOpenHelper {
    private static final String TAG = "MyDatabaseHelper";
    final static String DB_NAME="QL_TUYENSINH";
    final static String  TABLE_ID="id";
    // Sinh vien
    public final static String TABLE_STUDENT="The_Student";
    final static String NAME="name";
    final static  String DATE ="date";
    final static  String POINTS="point";
    final static  String CMND="cmnd";
    final static String PHONE="phone";
    final static String ADDRESS="address";
    final static  String CLASS="class";
    final static  String BRANCH="branch";
    private static final String CREATE_STUDENT_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_STUDENT + " (" +
            TABLE_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            NAME + " text not null, " +
            DATE + " text not null, " +
            POINTS + " text not null, " +
            CMND + " text not null, " +
            PHONE + " text not null, " +
            ADDRESS + " text not null, " +
            BRANCH + " text not null, " +
            CLASS + " text not null)";
    // Chi tieu
    public final static String TABLE_TARGET = "Chi_tieu";
    final static String TEN_NGANH = "name";
    final static String CHI_TIEU = "target";
    final static String NAM = "year";
    private static final String CREATE_TARGET_SQL = "create table if not exists Chi_tieu (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  name text not null, target text not null, year text not null )";
    // L
    public final static String TABLE_CLASS="Lop";
    final  static String CLASS_NAME="name";
    final static  String TEACHER ="teacher";
    final static String FACULTY="faculty";
    final static String NUMBER="number";
    private static final String CREATE_CLASS_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_CLASS + " (" +
            TABLE_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            CLASS_NAME + " text not null, " +
            TEACHER + " text not null, " +
            BRANCH + " text not null, " +
            FACULTY + " text not null, " +
            NUMBER + " text not null)";
    // Nganh
    public static final String TABLE_NGANH = "tblNganh";
    private static final String MA_NGANH = "maNganh";
    private static final String DIEM_CHUAN = "diemChuan";
    private static final String TEN_KHOA = "tenKhoa";
    private static final String TG_DAO_TAO = "thoiGianDaoTao";

    public static final String TABLE_KHOA = "tblKhoa";
    private static final String MA_KHOA = "maKhoa";

    private static final String CREATE_NGANH_SQL =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NGANH + " (" +
                    MA_NGANH + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                    + DIEM_CHUAN + " REAL NOT NULL," + TEN_NGANH + " TEXT NOT NULL,"
                    + TEN_KHOA + " TEXT NOT NULL,"   + TG_DAO_TAO + " TEXT NOT NULL" + ")";

    private static final String CREATE_KHOA_SQL =
            "CREATE TABLE IF NOT EXISTS " + TABLE_KHOA + " (" +
                    MA_KHOA + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    TEN_KHOA + " TEXT NOT NULL" + ")";
    // Tai khoan
    public final static String TABLE_USER="User";
    final static String  ID="user";
    final  static String Password="Password";
    private static final String CREATE_USER_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + " (" +
            ID + " STRING NOT NULL PRIMARY KEY," +
            Password + " text not null)";
    // Version
    final static int VERSION =1;
    Cursor cursor;
    Context context;
    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;

    public SQLHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    private static SQLHelper sInstance;
    public static SQLHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SQLHelper(context.getApplicationContext());
        }
        return sInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "onCreate: ");
        try
        {
            db.setLocale(Locale.getDefault());
            db.setVersion(1);
            db.execSQL(CREATE_STUDENT_SQL);
            db.execSQL(CREATE_TARGET_SQL);
            db.execSQL(CREATE_CLASS_SQL);
            db.execSQL(CREATE_NGANH_SQL);
            db.execSQL(CREATE_KHOA_SQL);
            db.execSQL(CREATE_USER_SQL);
        }
        catch (Exception e)
        {
            Log.e(TAG,e.toString());
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(TAG, "onUpgrade: ");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TARGET);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CLASS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NGANH);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_KHOA);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USER);
        onCreate(db);
    }

    // Thêm một sinh viên vào sqlite
    public void insertStudent(SinhVien k)
    {
        sqLiteDatabase=getWritableDatabase();
        contentValues=new ContentValues();
        // contentValues.put(TABLE_ID, k.getMaSV());
        contentValues.put(NAME, k.getHoTen());
        contentValues.put(DATE, k.getNgaySinh());
        contentValues.put(POINTS, k.getDiem());
        contentValues.put(PHONE, k.getSdt());
        contentValues.put(CMND, k.getCmnd());
        contentValues.put(ADDRESS, k.getNoiSinh());
        contentValues.put(CLASS, k.getLop());
        contentValues.put(BRANCH, k.getNganh());
        sqLiteDatabase.insert(TABLE_STUDENT, null, contentValues);
    }

    // Xóa một sinh viên  sqlite
    public void deleteStudent(int id)
    {
        sqLiteDatabase=getWritableDatabase();
        sqLiteDatabase.delete(TABLE_STUDENT, TABLE_ID+ "=?", new String[]{String.valueOf(id)});
    }

    // Sửa một sinh viên  sqlite
    public void updateStudent(SinhVien k)
    {
        sqLiteDatabase=getWritableDatabase();
        contentValues=new ContentValues();
        contentValues.put(NAME, k.getHoTen());
        contentValues.put(DATE, k.getNgaySinh());
        contentValues.put(POINTS, k.getDiem());
        contentValues.put(PHONE, k.getSdt());
        contentValues.put(CMND, k.getCmnd());
        contentValues.put(ADDRESS, k.getNoiSinh());
        contentValues.put(CLASS, k.getLop());
        sqLiteDatabase.update(TABLE_STUDENT, contentValues, TABLE_ID + "=?", new String[]{String.valueOf(k.getMaSV())});
    }

    // Sửa một sinh viên  sqlite
    public void updateStudentBranch(SinhVien k)
    {
        sqLiteDatabase=getWritableDatabase();
        contentValues=new ContentValues();
        contentValues.put(BRANCH, k.getNganh());
        sqLiteDatabase.update(TABLE_STUDENT, contentValues, TABLE_ID + "=?", new String[]{String.valueOf(k.getMaSV())});
    }

    // Lấy ra danh sách sinh viên  sqlite
    public ArrayList<SinhVien> getListStudent()
    {
        sqLiteDatabase=getReadableDatabase();
        ArrayList<SinhVien> list=new ArrayList<SinhVien>();
        SinhVien k;
        try
        {
            cursor=sqLiteDatabase.query(TABLE_STUDENT, null, null, null, null
                    , null, null, null);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        while(cursor.moveToNext())
        {
            int id=cursor.getInt(cursor.getColumnIndex(TABLE_ID));
            String name=cursor.getString(cursor.getColumnIndex(NAME));
            String date =cursor.getString(cursor.getColumnIndex(DATE));
            float point=cursor.getFloat(cursor.getColumnIndex(POINTS));
            String address=cursor.getString(cursor.getColumnIndex(ADDRESS));
            String phone =cursor.getString(cursor.getColumnIndex(PHONE));
            String cmnd =cursor.getString(cursor.getColumnIndex(CMND));
            String lop =cursor.getString(cursor.getColumnIndex(CLASS));
            String nganh =cursor.getString(cursor.getColumnIndex(BRANCH));
            //int ID, String ten, String diem, String ngayTao, String sdt, String diachi
            k=new SinhVien(id, name, point, date, phone, cmnd, address, lop, nganh);
            list.add(k);
        }

        return list;
    }

    public void CreateTable()
    {
        sqLiteDatabase=getReadableDatabase();
        sqLiteDatabase.execSQL(CREATE_STUDENT_SQL);
        sqLiteDatabase.execSQL(CREATE_TARGET_SQL);
        sqLiteDatabase.execSQL(CREATE_CLASS_SQL);
        sqLiteDatabase.execSQL(CREATE_NGANH_SQL);
        sqLiteDatabase.execSQL(CREATE_KHOA_SQL);
        sqLiteDatabase.execSQL(CREATE_USER_SQL);
    }

    public void DeleteTable()
    {
        sqLiteDatabase=getReadableDatabase();
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_STUDENT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_TARGET);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_CLASS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NGANH);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_KHOA);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_USER);
    }


    public void insertTarget(CHI_TIEU k) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        //contentValues.put(TABLE_ID, k.getMagv());
        contentValues.put(TEN_NGANH, k.getTenNganh());
        contentValues.put(CHI_TIEU, k.getchiTieu());
        contentValues.put(NAM, k.getnamHoc());
        sqLiteDatabase.insert(TABLE_TARGET, null, contentValues);
    }

    public void deleteTarget(int id) {
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(TABLE_TARGET, TABLE_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void updateTarget(CHI_TIEU k) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(TEN_NGANH, k.getTenNganh());
        contentValues.put(CHI_TIEU, k.getchiTieu());
        contentValues.put(NAM, k.getnamHoc());
        sqLiteDatabase.update(TABLE_TARGET, contentValues, TABLE_ID + "=?", new String[]{String.valueOf(k.getId())});
    }

    public ArrayList<CHI_TIEU> getListTarget() {
        sqLiteDatabase = getReadableDatabase();
        ArrayList<CHI_TIEU> list = new ArrayList<>();
        CHI_TIEU k;
        try {
            cursor = sqLiteDatabase.query(TABLE_TARGET, null, null, null, null
                    , null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(TABLE_ID));
            String name = cursor.getString(cursor.getColumnIndex(TEN_NGANH));
            String targer = cursor.getString(cursor.getColumnIndex(CHI_TIEU));
            String year = cursor.getString(cursor.getColumnIndex(NAM));
            //int ID, String ten, String diem, String ngayTao, String sdt, String diachi
            k = new CHI_TIEU(id, name, targer, year);
            list.add(k);
        }

        return list;
    }

    public void insertClass(Lop l)
    {
        sqLiteDatabase=getWritableDatabase();
        contentValues=new ContentValues();
        //contentValues.put(TABLE_ID, l.getID());
        contentValues.put(CLASS_NAME, l.getTenLop());
        contentValues.put(TEACHER, l.getGV());
        contentValues.put(BRANCH, l.getNganh());
        contentValues.put(FACULTY, l.getKhoa());
        contentValues.put(NUMBER, l.getSiso());
        sqLiteDatabase.insert(TABLE_CLASS, null, contentValues);
    }
    public void deleteClass(int id)
    {
        sqLiteDatabase=getWritableDatabase();
        sqLiteDatabase.delete(TABLE_CLASS, TABLE_ID+ "=?", new String[]{String.valueOf(id)});
    }
    public void updateClass(Lop l)
    {
        sqLiteDatabase=getWritableDatabase();
        contentValues=new ContentValues();
        contentValues.put(CLASS_NAME, l.getTenLop());
        contentValues.put(TEACHER, l.getGV());
        contentValues.put(BRANCH, l.getNganh());
        contentValues.put(FACULTY, l.getKhoa());
        contentValues.put(NUMBER, l.getSiso());
        sqLiteDatabase.update(TABLE_CLASS, contentValues, TABLE_ID + "=?", new String[]{String.valueOf(l.getMaLop())});
    }
    public ArrayList<Lop> getListClass()
    {
        sqLiteDatabase=getReadableDatabase();
        ArrayList<Lop> list=new ArrayList<>();
        Lop l;
        try
        {
            cursor=sqLiteDatabase.query(TABLE_CLASS, null, null, null, null
                    , null, null, null);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        while(cursor.moveToNext())
        {
            int id=cursor.getInt(cursor.getColumnIndex(TABLE_ID));
            String name=cursor.getString(cursor.getColumnIndex(CLASS_NAME));
            String teacher =cursor.getString(cursor.getColumnIndex(TEACHER));
            String branch=cursor.getString(cursor.getColumnIndex(BRANCH));
            String number=cursor.getString(cursor.getColumnIndex(NUMBER));
            String faculty =cursor.getString(cursor.getColumnIndex(FACULTY));
            //int ID, String tenlop, String GV, String nganh, String khoa, String siso

            l=new Lop(id, name, teacher, branch, faculty, number);
            list.add(l);
        }

        return list;
    }

    public boolean insertNGANH(NGANH nganh) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DIEM_CHUAN, nganh.getDiemChuan());
        values.put(TEN_NGANH, nganh.getTenNganh());
        values.put(TEN_KHOA, nganh.getKhoa());
        values.put(TG_DAO_TAO, nganh.getThoiGianDaoTao());

        long rowId = db.insert(TABLE_NGANH, null, values);
        if (rowId != -1)
            return true;
        return false;
    }

    public boolean insertKHOA(KHOA khoa) {
        /**
         * long insert(String table, String nullColumnHack, ContentValues values)
         * chèn một bản ghi trên các cơ sở dữ liệu. Bảng chỉ định tên bảng,
         * nullColumnHack không cho phép các giá trị hoàn toàn vô giá trị.
         * Nếu số thứ hai là null, android sẽ lưu trữ các giá trị null nếu giá trị
         * là trống rỗng. Đối số thứ ba xác định các giá trị được lưu trữ.
         */
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TEN_KHOA, khoa.getTenKhoa());

        long rowId = db.insert(TABLE_KHOA, null, values);
//        db.close();
        if (rowId != -1)
            return true;
        return false;
    }
    public ArrayList<NGANH> getAllNGANH() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<NGANH> ds_nganh = new ArrayList<NGANH>();
        String sql = "SELECT * FROM " + TABLE_NGANH;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                ds_nganh.add(new
                        NGANH(cursor.getInt(cursor.getColumnIndex(MA_NGANH)),
                        cursor.getFloat(cursor.getColumnIndex(DIEM_CHUAN)),
                        cursor.getString(cursor.getColumnIndex(TEN_NGANH)),
                        cursor.getString(cursor.getColumnIndex(TEN_KHOA)),
                        cursor.getString(cursor.getColumnIndex(TG_DAO_TAO))));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return ds_nganh;
    }

    public ArrayList<String> getTenNGANH() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> ds_ten_nganh = new ArrayList<String>();
        String sql = "SELECT " + TEN_NGANH + " FROM " + TABLE_NGANH;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                ds_ten_nganh.add(cursor.getString(cursor.getColumnIndex(TEN_NGANH)));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return ds_ten_nganh;
    }

    public ArrayList<String> getAllKHOA() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> ds_khoa = new ArrayList<String>();
        String sql = "SELECT tenKhoa FROM " + TABLE_KHOA;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                ds_khoa.add(cursor.getString(cursor.getColumnIndex(TEN_KHOA)));
            } while (cursor.moveToNext());
            cursor.close();
        }
//        db.close();
        return ds_khoa;
    }

    //Đếm tổng số dòng trong database
    public int getTotalWord(String tableName) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + tableName;
        Cursor cursor = db.rawQuery(sql, null);
        int totalRows = cursor.getCount();
        cursor.close();
        return totalRows;
    }

    public int updateNGANH(NGANH nganh) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DIEM_CHUAN, nganh.getDiemChuan());
        values.put(TEN_NGANH, nganh.getTenNganh());
        values.put(TEN_KHOA, nganh.getKhoa());
        values.put(TG_DAO_TAO, nganh.getThoiGianDaoTao());

        int rowEffect = db.update(TABLE_NGANH, values, MA_NGANH + " = ?",
                new String[]{String.valueOf(nganh.getMaNganh())});
        return rowEffect;
    }

    public int deleteNGANH(NGANH nganh) {
        SQLiteDatabase db = getReadableDatabase();
        int rowEffect = db.delete(TABLE_NGANH, MA_NGANH + " = ?", new
                String[]{String.valueOf(nganh.getMaNganh())});
        return rowEffect;
    }

    public void insertUser(User u)
    {
        sqLiteDatabase=getWritableDatabase();
        contentValues=new ContentValues();
        contentValues.put(ID, u.getID());
        contentValues.put(Password, u.getPassWord());
        sqLiteDatabase.insert(TABLE_USER, null, contentValues);
    }
    public void deleteUser(String id)
    {
        sqLiteDatabase=getWritableDatabase();
        sqLiteDatabase.delete(TABLE_USER, ID+ "=?", new String[]{String.valueOf(id)});
    }
    public void updateClass(User u)
    {
        sqLiteDatabase=getWritableDatabase();
        contentValues=new ContentValues();
        contentValues.put(Password, u.getPassWord());
        sqLiteDatabase.update(TABLE_USER, contentValues, ID + "=?", new String[]{String.valueOf(u.getID())});
    }
    public ArrayList<User> getListUser()
    {
        sqLiteDatabase=getReadableDatabase();
        ArrayList<User> list=new ArrayList<User>();
        User u;
        try
        {
            cursor=sqLiteDatabase.query(TABLE_USER, null, null, null, null
                    , null, null, null);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        while(cursor.moveToNext())
        {
            String id=cursor.getString(cursor.getColumnIndex(ID));
            String pw=cursor.getString(cursor.getColumnIndex(Password));

            u=new User(id, pw);
            list.add(u);
        }

        return list;
    }

    public int login(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_USER + " WHERE " + ID + " = '" + username + "' AND " + Password + " = '" + password + "'";
        Cursor cursor = db.rawQuery(sql, null);
        int totalRows = cursor.getCount();
        cursor.close();
        return totalRows;
    }

    public int deleteAllData(String TABLE_NAME) {
        SQLiteDatabase db = getReadableDatabase();
        int rowEffect = db.delete(TABLE_NAME, null,null);
//        db.close();
        return rowEffect;
    }
}