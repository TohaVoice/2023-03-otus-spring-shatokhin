package ru.otus.shatokhin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.shatokhin.domain.Genre;
import ru.otus.shatokhin.exception.EntityNotFoundException;
import ru.otus.shatokhin.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    @Transactional
    public void save(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> findAll() {
        return (List<Genre>) genreRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Genre findById(long id) {
        return genreRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Genre with id=%s is not found", id));
    }
}
