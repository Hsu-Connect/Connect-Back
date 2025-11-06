package hansung.hansung_connect.domain.link.converter;

import hansung.hansung_connect.domain.link.dto.LinkRequestDTO;
import hansung.hansung_connect.domain.link.dto.LinkResponseDTO;
import hansung.hansung_connect.domain.link.entity.Link;
import hansung.hansung_connect.domain.user.entity.User;

public class LinkConverter {

    public static Link toLink(LinkRequestDTO.CreateLinkDTO request, User user) {
        return Link.builder()
                .type(request.getType())
                .url(request.getUrl())
                .user(user)
                .build();
    }

    public static LinkResponseDTO.LinkResultDTO toLinkResultDTO(Link link) {
        return LinkResponseDTO.LinkResultDTO.builder()
                .id(link.getId())
                .type(link.getType())
                .url(link.getUrl())
                .build();
    }

    public static void applyUpdate(Link link, LinkRequestDTO.UpdateLinkDTO request) {
        link.update(request.getType(), request.getUrl());
    }
}
