package com.bh183.setiawan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_film";
    private final static String TABLE_FILM ="t_film";
    private final static String KEY_ID_FILM = "ID_Film";
    private final static String KEY_JUDUL = "Judul";
    private final static String KEY_TGL = "Release_Date";
    private final static String KEY_COVER = "Cover";
    private final static String KEY_GENRE = "Genre";
    private final static String KEY_DURATION = "Duration";
    private final static String KEY_RATING = "Rating";
    private final static String KEY_SYNOPSIS = "Synopsis";
    private Context context;


    public DatabaseHandler(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_FILM = "CREATE TABLE " + TABLE_FILM
                + "(" + KEY_ID_FILM + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUL + " TEXT, " + KEY_TGL + " DATE, "
                + KEY_COVER + " TEXT, " + KEY_GENRE + " TEXT, "
                + KEY_DURATION + " TEXT, " + KEY_RATING + " TEXT, " + KEY_SYNOPSIS + " TEXT);";

        db.execSQL(CREATE_TABLE_FILM);
        inisialisasiFilmAwal(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_FILM;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void tambahFilm(Film dataFilm){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, dataFilm.getRilis());
        cv.put(KEY_COVER, dataFilm.getCover());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_DURATION, dataFilm.getDurasi());
        cv.put(KEY_DURATION, dataFilm.getDurasi());
        cv.put(KEY_RATING, dataFilm.getRating());
        cv.put(KEY_SYNOPSIS, dataFilm.getSynopsis());


        db.insert(TABLE_FILM, null, cv);
        db.close();
    }

    public void tambahFilm(Film dataFilm, SQLiteDatabase db){
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, dataFilm.getRilis());
        cv.put(KEY_COVER, dataFilm.getCover());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_DURATION, dataFilm.getDurasi());
        cv.put(KEY_RATING, dataFilm.getRating());
        cv.put(KEY_SYNOPSIS, dataFilm.getSynopsis());
        db.insert(TABLE_FILM, null, cv);
    }

    public void editFilm(Film dataFilm){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, dataFilm.getRilis());
        cv.put(KEY_COVER, dataFilm.getCover());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_DURATION, dataFilm.getDurasi());
        cv.put(KEY_RATING, dataFilm.getRating());
        cv.put(KEY_SYNOPSIS, dataFilm.getSynopsis());

        db.update(TABLE_FILM, cv, KEY_ID_FILM + "=?", new String[]{String.valueOf(dataFilm.getIdFilm())});
        db.close();
    }

    public void hapusFilm (int idFilm){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_FILM, KEY_ID_FILM + "=?", new String[]{String.valueOf(idFilm)});
        db.close();
    }

    public ArrayList<Film> getAllFilm(){
        ArrayList<Film> dataFilm = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FILM;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if(csr.moveToFirst()){
            do {
                Film tempFilm = new Film(
                        csr.getInt(0),
                        csr.getString(1),
                        csr.getString(2),
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6),
                        csr.getString(7)

                );

                dataFilm.add(tempFilm);
            } while (csr.moveToNext());
        }

        return dataFilm;
    }

    private String storeImageFile(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inisialisasiFilmAwal(SQLiteDatabase db){
        int idFilm = 0;

        // Menambahkan data film ke 1
        Film film1 = new Film(
                idFilm,
                "Hobbs & Shaw",
                "2 Agustus 2019",
                storeImageFile(R.drawable.foto1),
                "Action, Adventure",
                "1hours 10minutes",
                "8,0",
                "Luke Hobbs (Dwayne Johnson) membentuk aliansi yang tidak mungkin dengan Deckard Shaw (Jason Statham). Keduanya terpaksa bersatu membantu Hattie Shaw (Vanessa Kirby) untuk memburu Brixton (Idris Elba). Brixton adalah penjahat jenis baru yang berhasil mengubah dirinya menjadi manusia super. Keberadaanya menjadi ancaman bagi umat manusia."
        );

        tambahFilm(film1, db);
        idFilm++;

        //  data film ke 2
        Film film2 = new Film(
                idFilm,
                "fast and furious 8",
                "12 April 2017",
                storeImageFile(R.drawable.foto2),
                "Action, Chrime, Thriller",
                "2hours 29minutes",
                "8,0 ",
                "Dom dijebak wanita misterius bernama Cipher - membuatnya membelot ke dunia terorisme. Para kru yang tersisa pun harus bersatu demi menghentikan aksi komplotan Cipher yang siap meluncurkan bom nuklir."
        );

        tambahFilm(film2, db);
        idFilm++;

        // data film ke 3
        Film film3 = new Film(
                idFilm,
                "Sonic the Hedgehog",
                "17 Februari 2020",
                storeImageFile(R.drawable.foto3),
                "Action, Adventure, Family, Sci-fi",
                "1hours 17minutes",
                "7,8 ",
                "Mengisahkan petualangan Sonic saat ia belajar tentang kompleksitas kehidupan di bumi bersama manusia sahabat barunya, Tom Wachowski. Sonic dan Tom bersatu untuk menghentikan si jahat Dr. Robotnik yang ingin menangkap Sonic dan menggunakan kekuatan istimewanya untuk menguasai dunia."
        );

        tambahFilm(film3, db);
        idFilm++;

        // data film ke 4
        Film film4 = new Film(
                idFilm,
                "scoob 2020",
                "15 Mei 2020",
                storeImageFile(R.drawable.foto4),
                "Adventure, Animation, Comedy, Family, Mystery",
                "1hours 10minutes",
                "7,0 ",
                "Dalam film Scoob! terlihat pertemuan pertama antara Shaggy dan Scooby-Doo kecil yang terjadi di pantai.\n" +
                        "\n" +
                        "Shaggy dengan cepat mengadopsinya dan segera merayakan Halloween bersama kemudian mereka bertemu Fred, Velma, dan Daphne.\n" +
                        "\n" +
                        "Mereka kemudian membentuk agen detektif yang dikenal dengan nama Mystery Inc.\n" +
                        "\n" +
                        "Maju ke beberapa tahun kemudian, Scooby dan geng menghadapi misteri terbsear dan paling menantang yang pernah ada yaitu rencana untuk melepaskan anjing hanti Cerberus ke dunia.\n" +
                        "\n" +
                        "Ketika mereka berlomba untuk menghentikan 'dogpocalypse' global, geng ini menemukan bahwa Scooby memiliki warisan rahasia dan takdir epik yang lebih besar daripada yang dibayangkan siapapun terutama Shaggy.\n" +
                        "\n" +
                        "Scooby kemudian diculik dan dibawa ke Falcon Fury.\n" +
                        "\n" +
                        "Sementara itu, sisa geng mencoba untuk memecahkan misteri ini."
        );

        tambahFilm(film4, db);
        idFilm++;

    }
}
