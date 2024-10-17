package team7.inplace.influencer;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
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
        MockedStatic<AuthorizationUtil> authorizationUtil = mockStatic(AuthorizationUtil.class);

        Influencer influencer1 = new Influencer("influencer1", "imgUrl1", "job1");
        Influencer influencer2 = new Influencer("influencer2", "imgUrl2", "job2");

        given(influencerRepository.findAll()).willReturn(Arrays.asList(influencer1, influencer2));
        given(AuthorizationUtil.getUserId()).willReturn(null);

        List<InfluencerInfo> influencerInfoList = influencerService.getAllInfluencers();

        assertThat(influencerInfoList).hasSize(2);
        assertThat(influencerInfoList.get(0).influencerName()).isEqualTo("influencer1");
        assertThat(influencerInfoList.get(0).likes()).isFalse();
        assertThat(influencerInfoList.get(1).influencerName()).isEqualTo("influencer2");
        assertThat(influencerInfoList.get(1).likes()).isFalse();

        authorizationUtil.close();
    }

    @Test
    public void getAllInfluencersTest_LoggedIn() {
        MockedStatic<AuthorizationUtil> authorizationUtil = mockStatic(AuthorizationUtil.class);

        Influencer influencer1 = new Influencer(1L, "influencer1", "imgUrl1", "job1");
        Influencer influencer2 = new Influencer(2L, "influencer2", "imgUrl2", "job2");
        Influencer influencer3 = new Influencer(3L, "influencer3", "imgUrl3", "job3");

        Long userId = 1L;
        User user = new User("name", "password", "nickname", UserType.KAKAO, Role.USER);

        given(influencerRepository.findAll()).willReturn(
            Arrays.asList(influencer1, influencer2, influencer3));
        given(AuthorizationUtil.getUserId()).willReturn(userId);

        // 2, 3번째 인플루언서 좋아요로 설정
        FavoriteInfluencer favoriteInfluencer1 = new FavoriteInfluencer(user, influencer2);
        favoriteInfluencer1.updateLike(true);
        FavoriteInfluencer favoriteInfluencer2 = new FavoriteInfluencer(user, influencer3);
        favoriteInfluencer2.updateLike(true);
        given(favoriteInfluencerRepository.findLikedInfluencerIdsByUserId(userId)).willReturn(
            Set.of(2L, 3L));

        List<InfluencerInfo> influencerInfoList = influencerService.getAllInfluencers();

        assertThat(influencerInfoList).hasSize(3);
        assertThat(influencerInfoList.get(0).influencerName()).isEqualTo("influencer2");
        assertThat(influencerInfoList.get(0).likes()).isTrue();
        assertThat(influencerInfoList.get(1).influencerName()).isEqualTo("influencer3");
        assertThat(influencerInfoList.get(1).likes()).isTrue();
        assertThat(influencerInfoList.get(2).influencerName()).isEqualTo("influencer1");
        assertThat(influencerInfoList.get(2).likes()).isFalse();

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
