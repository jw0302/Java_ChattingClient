package Model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ChatRoomModel {
	
	private String title;
	private UserModel host;
	private List<UserModel> entry;

}
