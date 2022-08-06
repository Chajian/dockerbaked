package com.ibs.dockerbacked.control;


import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin")
public class VerifyControl {

    @RequestMapping(value = "getLeagueRequest",method = RequestMethod.GET)
    public ResponseBody getLeagueRequests(@RequestParam(value = "league_id") int leagueId){
        return null;
    }
}
