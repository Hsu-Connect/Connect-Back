package hansung.hansung_connect.domain.link.service;

import hansung.hansung_connect.common.exception.GeneralException;
import hansung.hansung_connect.common.exception.code.status.ErrorStatus;
import hansung.hansung_connect.domain.link.converter.LinkConverter;
import hansung.hansung_connect.domain.link.dto.LinkResponseDTO;
import hansung.hansung_connect.domain.link.entity.Link;
import hansung.hansung_connect.domain.link.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LinkQueryServiceImpl implements LinkQueryService {

    private final LinkRepository linkRepository;

    @Override
    public LinkResponseDTO.LinkResultDTO getLink(Long linkId) {
        Link link = linkRepository.findById(linkId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.LINK_NOT_FOUND));

        return LinkConverter.toLinkResultDTO(link);
    }
}
