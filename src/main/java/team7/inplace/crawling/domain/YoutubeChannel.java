package team7.inplace.crawling.domain;

import static jakarta.persistence.EnumType.STRING;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "youtube_channel")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class YoutubeChannel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long influencerId;

    private String channelUUID;
    private String playListUUID;
    private String lastVideoUUID;

    @Enumerated(value = STRING)
    private ChannelType channelType;

    public String getChannelTypeCode() {
        return channelType.getCode();
    }

    public void updateLastVideoUUID(String lastVideoUUID) {
        this.lastVideoUUID = lastVideoUUID;
    }
}