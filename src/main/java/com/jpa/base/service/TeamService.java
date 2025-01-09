package com.jpa.base.service;

import com.jpa.base.domain.Team;
import com.jpa.base.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public Team save(String name) {
        log.info("TeamService.save()");
        Team team = new Team(name);

        return teamRepository.save(team);
    }
}
