package com.olgasadokierska.analogstory.user.mapper;
import com.olgasadokierska.analogstory.user.dtos.CartDTO;
import com.olgasadokierska.analogstory.user.dtos.FilmDTO;
import com.olgasadokierska.analogstory.user.model.Cart;
import com.olgasadokierska.analogstory.user.model.Film;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FilmMapper {
    FilmMapper INSTANCE = Mappers.getMapper(FilmMapper.class);

    @Mapping(target = "idCamera", source = "film.idCamera")
    FilmDTO filmToFilmDTO(Film film);

    @Mapping(target = "idCamera", source = "filmDTO.idCamera")
    Film filmDTOToFilm(FilmDTO filmDTO);
}
