package com.svalero.euroapi.service;
import com.svalero.euroapi.domain.*;
import com.svalero.euroapi.domain.dto.SongInDto;
import com.svalero.euroapi.domain.dto.SongOutDto;
import com.svalero.euroapi.exception.*;
import com.svalero.euroapi.repository.ArtistRepository;
import com.svalero.euroapi.repository.CountryRepository;
import com.svalero.euroapi.repository.EditionRepository;
import com.svalero.euroapi.repository.SongRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private EditionRepository editionRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<SongOutDto> getSongs(String title, int votes, String winner){
        List<Song> songList;
        if (!title.isEmpty()) {
            if (votes !=0 && !winner.isEmpty()) {
                songList = songRepository.findByTitleAndVotesAndWinner(title, votes, Boolean.valueOf(winner));
            } else if (votes != 0) {
                songList = songRepository.findByTitleAndVotes(title, votes);
            } else if (!winner.isEmpty()) {
                songList = songRepository.findByTitleAndWinner(title, Boolean.valueOf(winner));
            } else {
                songList = songRepository.findByTitle(title);
            }
        } else if (votes != 0) {
            if (!winner.isEmpty()) {
                songList = songRepository.findByVotesAndWinner(votes, Boolean.valueOf(winner));
            } else {
                songList = songRepository.findByVotes(votes);
            }
        } else if (!winner.isEmpty()) {
            songList = songRepository.findByWinner(Boolean.valueOf(winner));
        } else {
            songList = songRepository.findAll();
        }
        List<SongOutDto> songOutDtoList = new ArrayList<>();
        for (Song song : songList) {
            songOutDtoList.add(modelMapper.map(song, SongOutDto.class));
        }
        return songOutDtoList;
    }

    public SongOutDto getSongById(long id) throws SongNotFoundException{
        Optional<Song> songOptional = songRepository.findById(id);
        if (songOptional.isPresent()) {
            return modelMapper.map(songOptional.get(), SongOutDto.class);
        } else {
            throw new SongNotFoundException(id);
        }
    }

    public SongOutDto saveSong(SongInDto songInDto) throws ArtistNotFoundException, CountryNotFoundException, EditionNotFoundException {
        Song song = modelMapper.map(songInDto, Song.class);
        Long artistId = songInDto.getArtistId();
        Long countryId = songInDto.getCountryId();
        Long editionId = songInDto.getEditionId();

        Optional<Artist> artistOptional = artistRepository.findById(artistId);
        Optional<Country> countryOptional = countryRepository.findById(countryId);
        Optional<Edition> editionOptional = editionRepository.findById(editionId);

        if (artistOptional.isPresent()) {
            song.setArtist(artistOptional.get());
        } else {
            throw new ArtistNotFoundException(artistId);
        }

        if (countryOptional.isPresent()) {
            song.setCountry(countryOptional.get());
        } else {
            throw new CountryNotFoundException(countryId);
        }

        if (editionOptional.isPresent()) {
            song.setEdition(editionOptional.get());
        } else {
            throw new EditionNotFoundException(editionId);
        }
        return modelMapper.map(songRepository.save(song), SongOutDto.class);

    }

    public void removeSong (long songId) throws SongNotFoundException {
        if(songRepository.existsById(songId)){
            songRepository.deleteById(songId);
        } else {
            throw new SongNotFoundException(songId);
        }
    }

    public SongOutDto modifySong (long songId, SongInDto songInDto)
            throws SongNotFoundException, ArtistNotFoundException, CountryNotFoundException, EditionNotFoundException {
        Optional<Song> songOptional = songRepository.findById(songId);
        if (songOptional.isPresent()) {
            Song song = songOptional.get();
            modelMapper.map(songInDto, song);
            Long artistId = songInDto.getArtistId();
            Long countryId = songInDto.getCountryId();
            Long editionId = songInDto.getEditionId();

            Optional<Artist> artistOptional = artistRepository.findById(artistId);
            Optional<Country> countryOptional = countryRepository.findById(countryId);
            Optional<Edition> editionOptional = editionRepository.findById(editionId);

            if (artistOptional.isPresent()) {
                song.setArtist(artistOptional.get());
            } else {
                throw new ArtistNotFoundException(artistId);
            }

            if (countryOptional.isPresent()) {
                song.setCountry(countryOptional.get());
            } else {
                throw new CountryNotFoundException(countryId);
            }

            if (editionOptional.isPresent()) {
                song.setEdition(editionOptional.get());
            } else {
                throw new EditionNotFoundException(editionId);
            }

            return modelMapper.map(songRepository.save(song), SongOutDto.class);
        } else {
            throw new SongNotFoundException(songId);
        }
    }

    @ExceptionHandler(SongNotFoundException.class)
    public ResponseEntity<ErrorResponse> songNotFoundException(SongNotFoundException snfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, snfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ArtistNotFoundException.class)
    public ResponseEntity<ErrorResponse> artistNotFoundException(ArtistNotFoundException anfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, anfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<ErrorResponse> countryNotFoundException(CountryNotFoundException cnfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, cnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(EditionNotFoundException.class)
    public ResponseEntity<ErrorResponse> editionNotFoundException(EditionNotFoundException enfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, enfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
