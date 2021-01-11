package escalab.spring.nparrado.backend_spring.model;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "oauth_access_token")
@Data

public class OAuthAccessModel extends RepresentationModel<OAuthAccessModel> {

    @Id
    @Column(name = "token_id", length = 256)
    private String tokenId;

    @Column(name = "token")
    private byte[] token;

    @Column(name = "authentication_id", length = 256)
    private String authenticationId;

    @Column(name = "user_name", length = 256)
    private String userName;

    @Column(name = "client_id", length = 256)
    private String cliendId;

    @Column(name = "authentication")
    private byte[] authentication;

    @Column(name = "refresh_token", length = 256)
    private String refreshToken;
}
