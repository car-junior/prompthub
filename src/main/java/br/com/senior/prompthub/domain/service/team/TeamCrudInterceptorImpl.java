package br.com.senior.prompthub.domain.service.team;

import br.com.senior.prompthub.core.service.validate.CrudInterceptor;
import br.com.senior.prompthub.domain.entity.Team;
import org.springframework.stereotype.Component;

@Component
class TeamCrudInterceptorImpl implements CrudInterceptor<Team, Long> {
    @Override
    public void beforeCreate(Team entity) {
        entity.getMembers().forEach(member -> {
            member.setTeam(entity);
        });
    }

    @Override
    public void beforeUpdate(Long aLong, Team entity) {

    }

    @Override
    public void beforeDelete(Long aLong) {

    }
}
