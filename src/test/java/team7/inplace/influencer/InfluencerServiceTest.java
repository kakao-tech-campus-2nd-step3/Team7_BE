package team7.inplace.influencer;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import team7.inplace.favoriteInfluencer.domain.FavoriteInfluencer;
import team7.inplace.favoriteInfluencer.persistent.FavoriteInfluencerRepository;
import team7.inplace.influencer.application.InfluencerService;
import team7.inplace.influencer.application.dto.InfluencerCommand;
import team7.inplace.influencer.application.dto.InfluencerInfo;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.influencer.persistence.InfluencerRepository;
import team7.inplace.security.util.AuthorizationUtil;
import team7.inplace.user.domain.Role;
import team7.inplace.user.domain.User;
import team7.inplace.user.domain.UserType;

@ExtendWith(MockitoExtension.class)
public class InfluencerServiceTest {

    @Mock
    private InfluencerRepository influencerRepository;

    @Mock
    private FavoriteInfluencerRepository favoriteInfluencerRepository;

    @InjectMocks
    private InfluencerService influencerService;

    @Test
    public void getAllInfluencersTest_NotLoggedIn() {
        Pageable pageable = PageRequest.of(0, 10);
        MockedStatic<AuthorizationUtil> authorizationUtil = mockStatic(AuthorizationUtil.class);

        Influencer influencer1 = new Influencer("influencer1", "imgUrl1", "job1");
        Influencer influencer2 = new Influencer("influencer2", "imgUrl2", "job2");
        Page<Influencer> influencersPage = new PageImpl<>(List.of(influencer1, influencer2));

        given(influencerRepository.findAll(pageable)).willReturn(influencersPage);
        given(AuthorizationUtil.getUserId()).willReturn(null);

        Page<InfluencerInfo> influencerInfoPage = influencerService.getAllInfluencers(pageable);

        assertThat(influencerInfoPage).hasSize(2);
        assertThat(influencerInfoPage.getContent().get(0))
            .extracting("influencerName", "likes")
            .containsExactly(influencer1.getName(), false);
        assertThat(influencerInfoPage.getContent().get(1))
            .extracting("influencerName", "likes")
            .containsExactly(influencer2.getName(), false);

        authorizationUtil.close();
    }

    @Test
    public void getAllInfluencersTest_LoggedIn() {
        Pageable pageable = PageRequest.of(0, 10);
        MockedStatic<AuthorizationUtil> authorizationUtil = mockStatic(AuthorizationUtil.class);

        Influencer influencer1 = new Influencer(1L, "influencer1", "imgUrl1", "job1");
        Influencer influencer2 = new Influencer(2L, "influencer2", "imgUrl2", "job2");
        Influencer influencer3 = new Influencer(3L, "influencer3", "imgUrl3", "job3");
        Page<Influencer> influencersPage = new PageImpl<>(
            List.of(influencer1, influencer2, influencer3));

        Long userId = 1L;
        User user = new User("name", "password", "nickname", UserType.KAKAO, Role.USER);

        given(influencerRepository.findAll(pageable)).willReturn(influencersPage);
        given(AuthorizationUtil.getUserId()).willReturn(userId);

        // 2, 3번째 인플루언서 좋아요로 설정
        FavoriteInfluencer favoriteInfluencer1 = new FavoriteInfluencer(user, influencer2);
        favoriteInfluencer1.updateLike(true);
        FavoriteInfluencer favoriteInfluencer2 = new FavoriteInfluencer(user, influencer3);
        favoriteInfluencer2.updateLike(true);
        given(favoriteInfluencerRepository.findLikedInfluencerIdsByUserId(userId)).willReturn(
            Set.of(2L, 3L));

        Page<InfluencerInfo> influencerInfoPage = influencerService.getAllInfluencers(pageable);

        assertThat(influencerInfoPage).hasSize(3);
        assertThat(influencerInfoPage.getContent().get(0))
            .extracting("influencerName", "likes")
            .containsExactly(influencer2.getName(), true);
        assertThat(influencerInfoPage.getContent().get(1))
            .extracting("influencerName", "likes")
            .containsExactly(influencer3.getName(), true);
        assertThat(influencerInfoPage.getContent().get(2))
            .extracting("influencerName", "likes")
            .containsExactly(influencer1.getName(), false);

        authorizationUtil.close();
    }


    @Test
    public void createInfluencerTest() {
        InfluencerCommand command = new InfluencerCommand("name", "imgUrl", "job");
        Influencer influencer = new Influencer(1L, "name", "imgUrl", "job");
        given(influencerRepository.save(any(Influencer.class))).willReturn(influencer);

        Long savedId = influencerService.createInfluencer(command);

        assertThat(savedId).isEqualTo(influencer.getId());
    }

    @Test
    public void updateInfluencerTest() {
        InfluencerCommand command = new InfluencerCommand("updatedName", "updatedImgUrl",
            "updatedJob");
        Influencer influencer = new Influencer(1L, "name", "imgUrl", "job");
        given(influencerRepository.findById(any(Long.class))).willReturn(Optional.of(influencer));

        Long updatedId = influencerService.updateInfluencer(1L, command);

        assertThat(updatedId).isEqualTo(influencer.getId());
        assertThat(influencer.getName()).isEqualTo("updatedName");
        assertThat(influencer.getImgUrl()).isEqualTo("updatedImgUrl");
        assertThat(influencer.getJob()).isEqualTo("updatedJob");
    }

    @Test
    public void deleteInfluencerTest() {
        Influencer influencer = new Influencer(1L, "name", "imgUrl", "job");
        given(influencerRepository.findById(any(Long.class))).willReturn(Optional.of(influencer));

        influencerService.deleteInfluencer(1L);

        verify(influencerRepository, times(1)).delete(influencer);
    }
}
