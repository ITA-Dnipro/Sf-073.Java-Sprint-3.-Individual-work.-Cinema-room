package cinema.service;

import cinema.exeption.*;
import cinema.model.*;
import cinema.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CinemaServiceImpl implements CinemaService {
    private final SeatRepository seatRepository;

    public CinemaServiceImpl(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public Ticket purchase(Seat seat) {

        if (seatRepository.isBooked(seat)) {
            throw new AlreadyBookedException();
        }
        if (!seatRepository.isSeatPresent(seat)) {
            throw new SeatOutOfBoundsException();
        }
        return seatRepository.markSeatAsBooked(seat);
    }

    @Override
    public CinemaRoom getAllSeats() {
        return new CinemaRoom(seatRepository.getAvailableSeats().stream()
                .map(this::seatToSeatInfo).toList());
    }

    @Override
    public ReturnedTicket returnTicket(ReturnTicketRequest returnTicketRequest) {
        Ticket ticket;

        try{
             ticket = getTicketByToken(returnTicketRequest.getToken());
        } catch (NoSuchElementException ex){
            throw new WrongTokenException();
        }

        seatRepository.getTickets().remove(ticket);
        seatRepository.markSeatAsFree(seatInfoToSeat(ticket.getTicket()));

        return new ReturnedTicket(ticket.getTicket());
    }

    @Override
    public Stats listStats(String statsLogin) {
        System.out.println(statsLogin);
        if (statsLogin == null ||!statsLogin.equals("super_secret") ){
            throw new WrongPasswordException();
        }
        return new Stats(
                seatRepository.income(),
                seatRepository.getAvailableSeats().size(),
                seatRepository.getTickets().size());
    }

    private SeatInfo seatToSeatInfo(Seat seat) {
        return new SeatInfo(seat.row, seat.column);
    }

    private Seat seatInfoToSeat(SeatInfo seatInfo) {
        return new Seat(seatInfo.getRow(), seatInfo.getColumn());
    }

    private Ticket getTicketByToken(String token) {
        return seatRepository.getTickets().stream()
                .filter(ticket -> ticket.getToken().equals(token)).findFirst().get();
    }
}
