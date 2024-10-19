package team7.inplace.crawling.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QYoutubeChannel is a Querydsl query type for YoutubeChannel
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QYoutubeChannel extends EntityPathBase<YoutubeChannel> {

    private static final long serialVersionUID = 2131098359L;

    public static final QYoutubeChannel youtubeChannel = new QYoutubeChannel("youtubeChannel");

    public final EnumPath<ChannelType> channelType = createEnum("channelType", ChannelType.class);

    public final StringPath channelUUID = createString("channelUUID");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> influencerId = createNumber("influencerId", Long.class);

    public final StringPath lastVideoUUID = createString("lastVideoUUID");

    public final StringPath playListUUID = createString("playListUUID");

    public QYoutubeChannel(String variable) {
        super(YoutubeChannel.class, forVariable(variable));
    }

    public QYoutubeChannel(Path<? extends YoutubeChannel> path) {
        super(path.getType(), path.getMetadata());
    }

    public QYoutubeChannel(PathMetadata metadata) {
        super(YoutubeChannel.class, metadata);
    }

}

