package br.com.robertomassoni.mancala.rest.api.mapper;

import br.com.robertomassoni.mancala.core.domain.SowPit;
import br.com.robertomassoni.mancala.rest.api.request.SowPitRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SowPitMapper {

    SowPitMapper INSTANCE = Mappers.getMapper(SowPitMapper.class);

    SowPit mapFrom(final SowPitRequest source);
}
