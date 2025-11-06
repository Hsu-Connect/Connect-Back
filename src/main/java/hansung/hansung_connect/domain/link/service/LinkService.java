package hansung.hansung_connect.domain.link.service;

import hansung.hansung_connect.domain.link.dto.LinkRequestDTO;
import hansung.hansung_connect.domain.link.dto.LinkResponseDTO;

public interface LinkService {
    LinkResponseDTO.LinkResultDTO createLink(Long userId, LinkRequestDTO.CreateLinkDTO request);
}

