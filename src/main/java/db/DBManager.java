package db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import entities.Category;
import entities.Sentence;
import entities.Status;
import entities.Type;
import entities.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class DBManager
{
    private static ConnectionSource connectionSource;
    private static Dao<Word, Integer> wordDao;
    private static Dao<Category, Integer> catDao;
    private static Dao<Status, Integer> statusDao;
    private static Dao<Sentence, Integer> sentenceDao;
    private static String databaseUrl = "jdbc:sqlite:G:\\work\\VocMan\\src\\main\\resources\\word.db";
    private static List<Word> data;
    private static List<Sentence> sentenceData;
    public static void init() {

        try {
            connectionSource =
                            new JdbcConnectionSource(databaseUrl);
            wordDao = DaoManager.createDao(connectionSource, Word.class);
            catDao = DaoManager.createDao(connectionSource, Category.class);
            statusDao = DaoManager.createDao(connectionSource, Status.class);
            sentenceDao = DaoManager.createDao(connectionSource, Sentence.class);

        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public static void loadTopWords() {
        try {
            List<Word> wordList = wordDao.queryBuilder()
                            .orderBy("id", false)
                            .limit((long)30)
                            .query();
            data = wordList;
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public static void loadTopSentences(int count) {
        try {
            List<Sentence> sentenceList = sentenceDao.queryBuilder()
                            .orderBy("id", false)
                            .limit((long)count)
                            .query();
            sentenceData = sentenceList;
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public static List<Category> loadAllCategories() {
        try {
            return catDao.queryForAll();
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public static Category getCategory(int id) {
        try {
            return catDao.queryForId(id);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public static void saveWord(Word word, boolean syncProperties) {
        try {
            if(syncProperties) {
                word.syncProperties();
            }
            wordDao.createOrUpdate(word);
            loadTopWords();
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public static void saveSentence(Sentence  sentence) {
        try {
            sentenceDao.createOrUpdate(sentence);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public static void saveWord(Word word) {
        saveWord(word, true);
    }

    public static void close() {
        try {
            connectionSource.close();
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public static void main(String[] args)
    {
        String databaseUrl = "jdbc:sqlite:/Users/lhe/Documents/projects/VocMan/src/main/resources/word.db";
        try {
            ConnectionSource connectionSource =
                            new JdbcConnectionSource(databaseUrl);
            Dao<Word, Integer> wordDao = DaoManager.createDao(connectionSource, Word.class);
            Word word = wordDao.queryForId(1);
            System.out.println(word.getText());
            connectionSource.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static ObservableList<Word> getData() {
        return FXCollections.observableArrayList(data);
    }

    public static ObservableList<Sentence> getSentenceData() {
        return FXCollections.observableArrayList(sentenceData);
    }

    public static List<Word> getDataAsArrayList() {
        return data;
    }

    public static ObservableList<Word> searchWord(String keyword) {
        try {
            List<Word> wordList = wordDao.queryBuilder().where()
                            .like("text", "%" + keyword + "%")
                            .or()
                            .like("explanation", "%" + keyword + "%")
                            .query();
            return FXCollections.observableArrayList(wordList);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public static List<Word> getDeck(int deckSize) {
        try {
            List<Word> wordList = wordDao.queryBuilder().where()
                            .eq("status_id", Status.NEW)
                            .or()
                            .eq("status_id", Status.LEARNING)
                            .query();
            Collections.shuffle(wordList);
            return wordList.subList(0, deckSize);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public static List<Sentence> getSentenceQuizList(int deckSize) {
        try {
            List<Sentence> sentenceList = sentenceDao.queryForAll();
            Collections.shuffle(sentenceList);
            return sentenceList.subList(0, deckSize);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public static void deleteWord(Word word)
    {
        try {
            wordDao.delete(word);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public static Status getDefaultStatus() {
        try {
            return statusDao.queryForAll().get(0);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    public  static Status getStatusById(int id) {
        try {
            return statusDao.queryForId(id);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }
}
