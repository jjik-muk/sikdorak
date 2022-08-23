package com.jjikmuk.sikdorak.common;

import com.jjikmuk.sikdorak.review.domain.Review;
import com.jjikmuk.sikdorak.review.repository.ReviewRepository;
import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.store.repository.StoreRepository;
import com.jjikmuk.sikdorak.user.auth.domain.JwtProvider;
import com.jjikmuk.sikdorak.user.user.domain.User;
import com.jjikmuk.sikdorak.user.user.domain.UserRespository;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConfigurator implements InitializingBean {

    @PersistenceContext
    private EntityManager entityManager;
    private List<String> tableNames;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private JwtProvider jwtProvider;

    public Store store;
    public User user1;
    public User user2;
    public User followSendUser;
    public User followAcceptUser;
    public String user1ValidAuthorizationHeader;
    public String user2ValidAuthorizationHeader;
    public String followSendUserValidAuthorizationHeader;
    public String followAcceptUserValidAuthorizationHeader;
    public String user1RefreshToken;
    public String user1ExpiredRefreshToken;
    public String user1InvalidRefreshToken;
    public Review review;

    public void initDataSource() {
        initStoreData();
        initBasicUserData();
        initFollowingUserData();
        initUserAuthorizationData();
        initReviewData();
    }

    public void clear() {
        entityManager.unwrap(Session.class).doWork(this::cleanUpDatabase);
    }

    @Override
    public void afterPropertiesSet() {
        entityManager.unwrap(Session.class).doWork(this::extractTableNames);
    }


    // reference : https://www.baeldung.com/jdbc-database-metadata
    private void extractTableNames(Connection connection) throws SQLException {
        List<String> tableNames = new ArrayList<>();

        ResultSet tables = connection
            .getMetaData()
            .getTables(connection.getCatalog(), null, "%", new String[]{"TABLE"});

        try (tables) {
            while (tables.next()) {
                tableNames.add(tables.getString("table_name"));
            }

            this.tableNames = tableNames;
        }
    }

    private void cleanUpDatabase(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");

            for (String tableName : tableNames) {
                statement.executeUpdate("TRUNCATE TABLE " + tableName);
            }

            statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
        }
    }

    private void initStoreData() {
        this.store = storeRepository.save(new Store("맛있는가게",
            "02-0000-0000",
            "서울시 송파구 좋은길 1",
            "1층 101호",
            37.5093890,
            127.105143));
    }

    private void initBasicUserData() {
        this.user1 = userRespository.save(
            new User(12345678L, "test-user1", "https://profile1.com", "sikdorak1@gmail.com"));
        this.user2 = userRespository.save(
            new User(87654321L, "test-user2", "https://profile2.com", "sikdorak2@gmail.com"));
    }


    private void initFollowingUserData() {
        User sendUser = userRespository.save(
            new User(23456781L, "test-user3", "https://profile3.com", "sikdorak3@gmail.com"));
        User acceptUser = userRespository.save(
            new User(76543218L, "test-user4", "https://profile4.com", "sikdorak4@gmail.com"));

        sendUser.follow(acceptUser);

        this.followSendUser = userRespository.save(sendUser);
        this.followAcceptUser = userRespository.save(acceptUser);

    }

    private void initUserAuthorizationData() {
        String user1Payload = String.valueOf(this.user1.getId());
        String user2Payload = String.valueOf(this.user2.getId());
        String followSendUserPayload = String.valueOf(this.followSendUser.getId());

        Date now = new Date();
        Date accessTokenExpiredTime = new Date(now.getTime() + 1800000);

        this.user1ValidAuthorizationHeader =
            "Bearer " + jwtProvider.createAccessToken(user1Payload, accessTokenExpiredTime);
        this.user2ValidAuthorizationHeader =
            "Bearer " + jwtProvider.createAccessToken(user2Payload,accessTokenExpiredTime);
        this.followSendUserValidAuthorizationHeader =
            "Bearer " + jwtProvider.createAccessToken(followSendUserPayload, accessTokenExpiredTime);
        this.user1RefreshToken = jwtProvider.createRefreshToken(user1Payload, new Date(now.getTime()+8000000));
        this.user1ExpiredRefreshToken = jwtProvider.createRefreshToken(user1Payload, new Date(now.getTime() + 100));
        this.user1InvalidRefreshToken = jwtProvider.createRefreshToken(user1Payload, new Date(now.getTime() - 1000)) + "invalid";
    }

    private void initReviewData() {
        this.review = reviewRepository.save(new Review(this.user1.getId(),
            this.store.getId(),
            "Test review contents",
            3.f,
            "public",
            LocalDate.of(2022, 1, 1),
            List.of("tag1", "tag2"),
            List.of("https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg")));

        reviewRepository.save(new Review(this.followAcceptUser.getId(),
            this.store.getId(),
            "전체 공개된 리뷰 게시물",
            3.f,
            "public",
            LocalDate.of(2022, 1, 1),
            List.of("tag1", "tag2"),
            List.of("https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg")));

        reviewRepository.save(new Review(this.followAcceptUser.getId(),
            this.store.getId(),
            "친구 공개된 리뷰 게시물",
            3.f,
            "protected",
            LocalDate.of(2022, 1, 1),
            List.of("tag1", "tag2"),
            List.of("https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg")));

        reviewRepository.save(new Review(this.followAcceptUser.getId(),
            this.store.getId(),
            "비공개된 리뷰 게시물",
            3.f,
            "private",
            LocalDate.of(2022, 1, 1),
            List.of("tag1", "tag2"),
            List.of("https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg")));
    }
}
