package stage3.four.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stage3.four.exceptions.MissingOrWrongPasswordException;
import stage3.four.services.StatisticsService;

import static stage3.four.utils.Constants.POST_STATS_URL;

@RestController
public class StatisticController {

    @Autowired
    StatisticsService statisticsService;

    @PostMapping(path = POST_STATS_URL)
    public ResponseEntity<Object> getStatistics(@RequestParam(required = false) String password){
        if (!"pass123".equals(password)) {
            throw new MissingOrWrongPasswordException();
        }
        return statisticsService.getStatistics();
    }
}
