package ru.bogdanov.poem.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Владимир on 15.09.2016.
 */
public class DBHelper extends SQLiteOpenHelper{
    private final static String DB_NAME="db";
    private final static String LOG="db_info";
    private final static int DB_VERSION=1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + DBNamespace.TABLE_NAME +
                "(" + "id" + " integer primary key," +
                DBNamespace.POEM_NAME + " text" + ")");

        addToDB(sqLiteDatabase, "В дверях эдема ангел нежный\n" +
                "Главой поникшею сиял,\n" +
                "А демон мрачный и мятежный\n" +
                "Над адской бездною летал.\n" +
                "\n" +
                "Дух отрицанья, дух сомненья\n" +
                "На духа чистого взирал\n" +
                "И жар невольный умиленья\n" +
                "Впервые смутно познавал.\n" +
                "\n" +
                "\"Прости,- он рек,- тебя я видел,\n" +
                "И ты недаром мне сиял:\n" +
                "Не все я в небе ненавидел,\n" +
                "Не все я в мире презирал\". ");
        addToDB(sqLiteDatabase, "Засуха.Воздух – почти наждак. \n " +
                "Небо – живой свинец,\n" +
                        "Глухо рокочет, но нет дождя,\n" +
                        "А без дождя – конец.\n" +
                        "\n" +
                        "Вянет раскидистый старый дуб,\n" +
                        "Жухлой трясет листвой.\n" +
                        "Озеро съежилось в бурый суп,\n" +
                        "Илистый и густой.\n" +
                        "\n" +
                        "Люди долины собрали круг,\n" +
                        "Каждый стучит в там-там –\n" +
                        "Духи на небе не слышат стук\n" +
                        "Или уснули там.\n" +
                        "\n" +
                        "– Надо на небо послать гонца, –\n" +
                        "Молвил усталый вождь, –\n" +
                        "Сильного воина, не юнца,\n" +
                        "Он принесет нам дождь.\n" +
                        "  \n" +
                        "К Верхнему миру не близок путь:\n" +
                        "День, еще ночь и день; \n" +
                        "Много опасностей, хмарь и жуть...\n" +
                        "Только Большой Олень\n" +
                        "  \n" +
                        "Сможет пройти, и без лишних драк,\n" +
                        "Нам не нужна вражда. \n" +
                        "Он одолеет и страх, и мрак.\n" +
                        "Он – Человек Дождя!\n" +
                        "  \n" +
                        "... Обнял невесту, отца и мать,\n" +
                        "Выбрал копьё и лук,\n" +
                        "Кажется, что-то хотел сказать,\n" +
                        "Странно осекся вдруг,\n" +
                        "\n" +
                        "Лег на алтарь и закрыл глаза \n" +
                        "Воин, что стоил двух.\n" +
                        "Тихо отправился в небеса \n" +
                        "Добрый и сильный дух.\n" +
                        "  \n" +
                        "... Гулко там-тамы поют дождю –\n" +
                        "Ливень стеной весь день.\n" +
                        "Ночью невеста ушла к вождю.\n" +
                        "Плачет Большой Олень. ");
        addToDB(sqLiteDatabase, "So, we’ll go no more a-roving\n" +
                "So late into the night,\n" +
                "Though the heart be still as loving,\n" +
                "And the moon be still as bright.\n" +
                "For the sword outwears its sheath,\n" +
                "And the soul wears out the breast,\n" +
                "And the heart must pause to breathe,\n" +
                "And love itself have rest.\n" +
                "Though the night was made for loving,\n" +
                "And the day returns too soon,\n" +
                "Yet we’ll go no more a-roving\n" +
                "By the light of the moon.");
    }

    public void addToDB(SQLiteDatabase db, String text){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBNamespace.POEM_NAME,text);
        Log.i(LOG,"Inserted");
        db.insert(DBNamespace.TABLE_NAME,null,contentValues);
    }

    public void deletePoem(SQLiteDatabase db,String text){
        db.delete(DBNamespace.TABLE_NAME, DBNamespace.POEM_NAME+"='"+text+"'",null);
    }

    public ArrayList<String> getPoemArray(SQLiteDatabase db){
        ArrayList<String> list=new ArrayList<>();
        Cursor cursor=db.query(DBNamespace.TABLE_NAME,new String[]{DBNamespace.POEM_NAME},null,null,null,null,null);
        cursor.moveToFirst();
        do {
            String text=cursor.getString(cursor.getColumnIndex(DBNamespace.POEM_NAME));
            list.add(text);
            Log.i(LOG,"Getted");
        } while (cursor.moveToNext());
        Collections.reverse(list);
        cursor.close();
        return list;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
