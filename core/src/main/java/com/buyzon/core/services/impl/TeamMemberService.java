package com.buyzon.core.services.impl;

import com.buyzon.core.services.TeamMemberDetails;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

@Designate(ocd= TeamMemberDetails.class)
@Component(service= TeamMemberService.class)
public class TeamMemberService {


    private String message;

    @Activate
    protected void activate(TeamMemberDetails config){
        this.message=config.message();
    }

    public String getMessage() {
        return message;
    }

}
