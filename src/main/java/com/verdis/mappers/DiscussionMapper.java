package com.verdis.mappers;

import com.verdis.dtos.DiscussionDto;
import com.verdis.dtos.DiscussionPreviewDto;
import com.verdis.models.Discussion;
import jakarta.validation.constraints.NotNull;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {AccountMapper.class})
public interface DiscussionMapper {

    @Mapping(target = "archived", expression = "java(discussion.getArchivedBy() != null)")
    @Mapping(target = "author", source = "author", qualifiedByName = "mapAccount")
    DiscussionPreviewDto toPreviewDto(Discussion discussion);

    @Mapping(target = "author", source = "author", qualifiedByName = "mapAccount")
    DiscussionDto toDto(@NotNull Discussion discussion);
}

