package hansung.hansung_connect.domain.link.service;

import hansung.hansung_connect.domain.link.dto.LinkRequestDTO;
import hansung.hansung_connect.domain.link.dto.LinkResponseDTO;

public interface LinkCommandService {
    LinkResponseDTO.LinkResultDTO createLink(Long userId, LinkRequestDTO.CreateLinkDTO request);

    LinkResponseDTO.LinkResultDTO updateLink(Long userId, Long linkId, LinkRequestDTO.UpdateLinkDTO request);

    LinkResponseDTO.LinkResultListDTO createLinks(Long userId, LinkRequestDTO.CreateLinksDTO request);

}

