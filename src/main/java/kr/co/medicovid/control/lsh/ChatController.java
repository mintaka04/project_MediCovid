package kr.co.medicovid.control.lsh;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.medicovid.dao.lsh.ChatDAO;
import kr.co.medicovid.dto.ChatDTO;

@Controller
public class ChatController {

	@Autowired
	private SimpMessagingTemplate smt;

	@Autowired
	private ChatDAO dao;

//	@MessageMapping("/hello")
//	public void send(SimpMessageHeaderAccessor sha, @Payload String username) {
//		String message = "Hello from " + sha.getUser().getName();
//		
//		// 첫번째 파라미터 클라이언트가 웹소켓에 연결시 생성되는 세션아이디와 연관된 값, 값 안주면 null
//		// 두번째 있는 "/queue/messages" 값이 클라이언트가 구독해야 할 endpoint
//		smt.convertAndSendToUser(username, "/queue/messages", message); 
//	}

//	@MessageMapping("/hello")
//	public void send(SimpMessageHeaderAccessor sha, @Payload ChatDTO chatMessage) {
//
//		smt.convertAndSendToUser(chatMessage.getReceiver(), "/queue/messages", chatMessage);
//	}

	@MessageMapping("/hello")
	public void send(@Payload ChatDTO chatMessage) {
		dao.insertChat(chatMessage.getSender(), chatMessage.getReceiver(), chatMessage.getContent(),
				chatMessage.getChatTime());

		if (chatMessage.getSender().equals("admin")) {
			smt.convertAndSend("/room/" + chatMessage.getReceiver(), chatMessage);
		} else {
			smt.convertAndSend("/room/" + chatMessage.getSender(), chatMessage);
		}
	}

	@RequestMapping("/chat/userChat")
	public String userChat() {
		return "userChat";
	}

	@RequestMapping("/chat/adminChat")
	public String aminChat(Model model) {
		model.addAttribute("list", dao.selectChatRoomList());
		return "adminChat";
	}

	@GetMapping("/chat")
	@ResponseBody
	public List<ChatDTO> selectChatRoomList() {
		return dao.selectChatRoomList();
	}

	@GetMapping("/chat/{username}")
	@ResponseBody
	public List<ChatDTO> getChatByUsername(@PathVariable("username") String username) {
		dao.updateStatusToRead(username);
		return dao.selectChatBySender(username);
	}

	@PutMapping("/chat/{username}")
	public void changeStatusToRead(@PathVariable("username") String username) {
		dao.updateStatusToRead(username);
	};

}
