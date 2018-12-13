package axsoup.data;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import axsoup.Ingredient;
import axsoup.Soup;



@Repository
public class JdbcSoupRepository implements SoupRepository {
    private JdbcTemplate jdbc;
    public JdbcSoupRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    @Override
    public Soup save(Soup soup) {
        long soupId = saveSoupInfo(soup);
        System.out.println(7);
        soup.setId(soupId);
        for (Ingredient ingredient : soup.getIngredients()) {
            saveIngredientToSoup(ingredient, soupId);
        }

        return soup;
    }
    private long saveSoupInfo(Soup soup) {
        System.out.println(1);
        soup.setCreatedAt(new Date());
        System.out.println(2);
        PreparedStatementCreator psc =
                new PreparedStatementCreatorFactory(
                        "insert into Soup (name, createdAt) values (?, ?)",
                        Types.VARCHAR, Types.TIMESTAMP
                ).newPreparedStatementCreator(
                        Arrays.asList(
                                soup.getName(),
                                new Timestamp(soup.getCreatedAt().getTime())));
        System.out.println(3);
        System.out.println(psc);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        System.out.println(4);

        jdbc.update(psc, keyHolder);
        System.out.println(5);

        System.out.println(psc);
        System.out.println(keyHolder.toString());
//        System.out.println(keyHolder.getKey().longValue());

        return keyHolder.getKey().longValue();
    }

    private void saveIngredientToSoup(
            Ingredient ingredient, long soupId) {
        System.out.println(6);
        jdbc.update(
                "insert into Soup_Ingredients (soup, ingredient) " +
                        "values (?, ?)",
                soupId, ingredient.getId());
    }
}

