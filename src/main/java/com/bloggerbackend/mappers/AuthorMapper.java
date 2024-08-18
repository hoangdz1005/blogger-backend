package com.bloggerbackend.mappers;

import com.bloggerbackend.entities.Account;
import com.bloggerbackend.entities.Author;
import com.bloggerbackend.enums.AccountStatus;
import com.bloggerbackend.models.request.AccountReq;
import com.bloggerbackend.models.request.AuthorReq;
import com.bloggerbackend.models.response.AuthorRes;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorMapper {

    public void updateAuthor(Author to, AuthorReq from) {
        to.setName(from.getName());
        to.setAddress(from.getAddress());
        to.setAvatarImg(from.getAvatarImg());
        to.setJoinedDate(LocalDateTime.now());
        to.setDob(from.getDob());
        to.setProfile(from.getProfile());
        to.setSocialLink(from.getSocialLink());
    }

    public Author mapToAuthor(AccountReq from) {
        Author to = new Author();
        to.setName(from.getUsername());
        to.setEmail(from.getEmail());
        to.setStatus(AccountStatus.ACTIVE);
        to.setJoinedDate(LocalDateTime.now());
        return to;
    }

    public AuthorRes mapToAuthorRes(Author from) {
        AuthorRes to = new AuthorRes();
        to.setId(from.getId());
        to.setName(from.getName());
        to.setAddress(from.getAddress());
        to.setEmail(from.getEmail());
        to.setStatus(from.getStatus().name());
        to.setAvatarImg(from.getAvatarImg());
        to.setJoinedDate(from.getJoinedDate());
        to.setDob(from.getDob());
        to.setProfile(from.getProfile());
        to.setSocialLink(from.getSocialLink());
        return to;
    }
    public List<AuthorRes> mapToListAuthorRes(List<Author> from) {
        List<AuthorRes> to = new ArrayList<AuthorRes>();
        from.forEach(author -> to.add(mapToAuthorRes(author)));
        return to;
    }
}

