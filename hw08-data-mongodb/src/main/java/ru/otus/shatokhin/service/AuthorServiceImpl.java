package ru.otus.shatokhin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.shatokhin.domain.Author;
import ru.otus.shatokhin.exception.DocumentNotFoundException;
import ru.otus.shatokhin.repository.AuthorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public void save(Author author) {
        authorRepository.save(author);
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author findById(String id) {
        return authorRepository.findById(id).orElseThrow(() ->
                new DocumentNotFoundException("Author with id=%s is not found", id));
    }
}
