package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.Genre;
import by.library.yavlash.repository.GenreRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreRepositoryImpl extends AbstractRepositoryImpl<Genre> implements GenreRepository {
    private static final String ID_COLUMN = "id";
    private static final String GENRE_NAME_COLUMN = "genre_name";

    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM genres WHERE id=?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM genres";
    private static final String INSERT_QUERY = "INSERT INTO genres (genre_name) VALUES (?)";
    private static final String UPDATE_QUERY = "UPDATE genres SET genre_name=? WHERE id=?";
    private static final String DELETE_QUERY = "DELETE FROM genres WHERE id=?";

    private static final String DELETE_BOOK_GENRE_LINKS_QUERY = "DELETE FROM book_genre_links WHERE genre_id=?";

    public GenreRepositoryImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getSelectByIdQuery() {
        return SELECT_BY_ID_QUERY;
    }

    @Override
    protected String getSelectAllQuery() {
        return SELECT_ALL_QUERY;
    }

    @Override
    protected String getInsertQuery() {
        return INSERT_QUERY;
    }

    @Override
    protected String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected String getDeleteQuery() {
        return DELETE_QUERY;
    }

    @Override
    protected Genre construct(ResultSet resultSet) throws SQLException {
        return Genre.builder()
                .id(resultSet.getLong(ID_COLUMN))
                .genreName(resultSet.getString(GENRE_NAME_COLUMN))
                .build();
    }

    @Override
    protected void settingPreparedStatement(PreparedStatement preparedStatement, Genre genre) throws SQLException {
        preparedStatement.setString(1, genre.getGenreName());
    }

    @Override
    protected void deleteLinks(Connection connection, Long genreId) throws SQLException {
        deleteGenreLinks(connection, genreId);
    }

    protected void deleteGenreLinks(Connection connection, Long id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GenreRepositoryImpl.DELETE_BOOK_GENRE_LINKS_QUERY)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }
}