package hansung.hansung_connect.domain.link.converter;

import hansung.hansung_connect.domain.link.dto.LinkRequestDTO;
import hansung.hansung_connect.domain.link.dto.LinkResponseDTO;
import hansung.hansung_connect.domain.link.dto.LinkResponseDTO.LinkResultDTO;
import hansung.hansung_connect.domain.link.entity.Link;
import hansung.hansung_connect.domain.user.entity.User;
import java.util.List;

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

    public static List<Link> toLinkList(List<LinkRequestDTO.CreateLinkDTO> requests, User user) {
        return requests.stream()
                .map(dto -> toLink(dto, user))
                .toList();
    }

    public static LinkResponseDTO.LinkResultListDTO toLinkResultListDTO(List<Link> links) {
        List<LinkResultDTO> results = links.stream()
                .map(LinkConverter::toLinkResultDTO)
                .toList();

        return LinkResponseDTO.LinkResultListDTO.builder()
                .links(results)
                .build();
    }
}
