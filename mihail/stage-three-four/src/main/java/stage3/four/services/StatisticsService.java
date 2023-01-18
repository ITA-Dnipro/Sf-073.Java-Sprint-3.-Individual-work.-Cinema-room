package stage3.four.services;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import stage3.four.models.Ticket;
import stage3.four.models.TicketData;
import stage3.four.modelsDTO.StatsDTO;
import stage3.four.repositories.TicketRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StatisticsService {

    private final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    @Autowired
    TicketRepository ticketRepository;

    public List<TicketData> getAllPurchasedTickets(){
        return ticketRepository.getTicketTable().values()
                .stream()
                .filter(TicketData::isPurchased)
                .collect(Collectors.toList());
    }

    public int getAllAvailableSeats(){
        return ticketRepository.getTicketTable().size()-getAllPurchasedTickets().size();
    }

    public int calculateCurrentIncome(){
        List<TicketData> ticketDataList = getAllPurchasedTickets();
        List<Ticket> ticketList = ticketDataList.stream().map(TicketData::getTicket).collect(Collectors.toList());
        return ticketList.stream().map(Ticket::getPrice).reduce(0, Integer::sum);
    }

    public ResponseEntity<Object> getStatistics() {
        int currentIncome = calculateCurrentIncome();
        int numberOfAvailableSeats = getAllAvailableSeats();
        int numberOfPurchasedTickets = getAllPurchasedTickets().size();
        StatsDTO statsDTO = new StatsDTO(currentIncome, numberOfAvailableSeats, numberOfPurchasedTickets);
        return new ResponseEntity<>(gson.toJson(statsDTO), HttpStatus.OK);
    }
}
