package antifraud.rest.dto;

import antifraud.domain.model.User;

public record DeletedUserDTO(String username,
                             String status) {
    public static DeletedUserDTO fromModel(User deletedUser) {
        String statusMessage = "Deleted successfully!";
        return new DeletedUserDTO(deletedUser.getUsername(),
                statusMessage);
    }
}
