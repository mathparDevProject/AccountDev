package mathpar.web.learning.account.utils.dto.responses;

import lombok.Data;
import mathpar.web.learning.account.entities.Account;

import java.util.Date;

@Data
public class AccountResponse {
    private long accountId;
    private String email;
    private String fullName;
    private Date creationDate;

    public AccountResponse(Account account){
        this.accountId = account.getId();
        this.email = account.getEmail();
        this.fullName = account.getFullName();
        this.creationDate = account.getCreationDate();
    }
}
