package axsoup.data;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

import axsoup.Ingredient;
import axsoup.Soup;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;




@Repository
public class JdbcSoupRepository implements SoupRepository {
    private JdbcTemplate jdbc;
    public JdbcSoupRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    @Override
    public Soup save(Soup soup) {
        long soupId = saveSoupInfo(soup);
        soup.setId(soupId);
        for (Ingredient ingredient : soup.getIngredients()) {
            saveIngredientToSoup(ingredient, soupId);
        }

        return soup;
    }
    private long saveSoupInfo(Soup soup) {
        soup.setCreatedAt(new Date());
        PreparedStatementCreator psc = new PreparedStatementCreatorFactory(
                        "insert into Soup (name, createdAt) values (?, ?)",
                        Types.VARCHAR, Types.TIMESTAMP
                ).newPreparedStatementCreator(
                        Arrays.asList(
                                soup.getName(),
                                new Timestamp(soup.getCreatedAt().getTime())));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);
        return keyHolder.getKey().longValue();
    }
    private void saveIngredientToSoup(
            Ingredient ingredient, long soupId) {
        jdbc.update(
                "insert into Soup_Ingredients (soup, ingredient) " +
                        "values (?, ?)",
                soupId, ingredient.getId());
    }
}

