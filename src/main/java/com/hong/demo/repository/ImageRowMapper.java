package com.hong.demo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.hong.demo.domain.Image;

@Component
public class ImageRowMapper implements RowMapper<Image> {
    @Override
    public Image mapRow(ResultSet rs, int rowNum) throws SQLException {
        Image image = new Image();
        image.setBookId(rs.getInt("book_id"));
        image.setId(rs.getInt("id"));
        image.setUuid(rs.getString("uuid"));
        image.setTitle(rs.getString("title"));
        return image;
    }
}
