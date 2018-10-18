package com.davidfacsko.bootstrap;

import com.davidfacsko.domain.Guest;
import com.davidfacsko.domain.Reservation;
import com.davidfacsko.domain.Review;
import com.davidfacsko.domain.Room;
import com.davidfacsko.domain.User;
import com.davidfacsko.domain.enumeration.RoomType;
import com.davidfacsko.repository.AuthorityRepository;
import com.davidfacsko.repository.GuestRepository;
import com.davidfacsko.repository.ReservationRepository;
import com.davidfacsko.repository.ReviewRepository;
import com.davidfacsko.repository.RoomRepository;
import com.davidfacsko.repository.UserRepository;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Created by jt on 10/14/17.
 */
@Component
public class HotelBootstrap implements CommandLineRunner {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    
    private final String text1 = "Bacon ipsum dolor amet t-bone tenderloin sirloin shankle fatback, pancetta cow ground round tri-tip. Venison ground round salami tongue pork chop chuck shank. Short loin bacon hamburger pork. Hamburger meatloaf alcatra short ribs.\r\n" + 
			"\r\n" + 
			"Rump sirloin cupim prosciutto salami pig. Sausage ground round jowl boudin t-bone, salami capicola short ribs doner. Venison tri-tip shoulder pastrami kielbasa tongue burgdoggen pork belly frankfurter short loin meatloaf shank ham hock jerky jowl. Tri-tip beef ribs meatball swine alcatra flank sausage doner. Corned beef ham strip steak shank cow turkey, hamburger rump ball tip.\r\n" + 
			"\r\n" + 
			"Tongue meatloaf spare ribs, corned beef ribeye sirloin pork belly bacon ham hock turducken leberkas. Ribeye shoulder filet mignon brisket, pastrami meatball fatback salami tongue kevin venison frankfurter porchetta chuck. Pig picanha shoulder landjaeger sirloin. Pork landjaeger sirloin ham hock porchetta t-bone.";
			
	private final String text2 = "Spicy jalapeno bacon ipsum dolor amet t-bone shoulder in laborum labore jerky anim sunt drumstick, aliquip velit eiusmod tongue et tempor. Labore drumstick shank jerky laboris ribeye pastrami ea et commodo nisi id. Kevin brisket chuck pork loin ex, dolore minim ribeye mollit fugiat spare ribs laborum. Consequat cow fugiat in buffalo, landjaeger sed ham hock. Beef laborum eu bresaola rump ham hock commodo biltong jowl dolore buffalo in andouille pork chop cupim.\r\n" + 
			"\r\n" + 
			"Ad meatball culpa pork chop hamburger, jerky adipisicing nostrud mollit. Enim duis buffalo in drumstick ham jowl commodo chicken short ribs corned beef. Enim commodo minim sausage ex tempor beef ribs ullamco est shoulder picanha ut hamburger. Officia t-bone ad, in veniam ham hock ex non. Minim deserunt sed, dolor biltong in shank ham hock eiusmod esse leberkas pastrami cupidatat et id. Magna in labore, beef landjaeger fatback jerky.\r\n" + 
			"\r\n" + 
			"Commodo fatback culpa, brisket porchetta tail ut burgdoggen venison andouille ex. Occaecat pastrami officia, mollit voluptate eiusmod buffalo nisi. Velit pastrami buffalo, ut filet mignon do kielbasa. Dolore in doner jowl, pork sausage venison shoulder pancetta reprehenderit et bresaola culpa shankle.";

    private final String imgBasicPath = "src/main/resources/images/BasicRoom.jpg";
    private final String imgDeluxePath = "src/main/resources/images/DeluxeRoom.jpg";
    private final String imgSuperiorPath = "src/main/resources/images/SuperiorRoom.jpg";
	
    // If a bean has one constructor, you can omit the @Autowired
    // https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-spring-beans-and-dependency-injection.html
    public HotelBootstrap(ReservationRepository reservationRepository, RoomRepository roomRepository,
                        ReviewRepository reviewRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
    }
    @Transactional
    @Override
    public void run(String... strings) throws Exception {

        // init Reservations
        if(reservationRepository.count() == 0){
            //only load data if no data loaded
            initData();
        }

    }

    private void initData() {
    	User bob = getUser("Bob", "password", "bob", "bob@gmail.com", true, "ROLE_USER");
    	User george = getUser("George", "password", "george", "george@gmail.com", true, "ROLE_USER");
    	getReview(4, text1, bob);
    	getReview(5, text2, george);
    	Room room1 = getRoom(2, RoomType.BASIC, 1, imgBasicPath);
    	Room room2 = getRoom(2, RoomType.DELUXE, 2, imgDeluxePath);
    	Room room3 = getRoom(1, RoomType.SUPERIOR, 3, imgSuperiorPath);
    	Reservation res1 = getReservation("2019-02-10", "2019-02-15", room1, bob);
    	Reservation res2 = getReservation("2019-02-14", "2019-02-16", room2, george);
    	Reservation res3 = getReservation("2019-03-12", "2019-03-17", room3, bob);
//    	Reservation res4 = getReservation("2019-02-08", "2019-02-10", room2, bob);
//    	Reservation res5 = getReservation("2019-02-13", "2019-02-18", room3, george);
//    	Reservation res6 = getReservation("2019-03-11", "2019-02-18", room2, bob);
    }
    
    
    private Room getRoom(int numOfBeds, RoomType roomType, int roomNum, String imgPath) {
		Room room = new Room();
		room.setNumOfBeds(numOfBeds);
		room.setType(roomType);
		room.setRoomNumber(roomNum);
		try {
			byte[] array = Files.readAllBytes(new File(imgPath).toPath());
			room.setImage(array);
			room.setImageContentType("image/jpeg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return roomRepository.save(room);
	}
	private Reservation getReservation(String checkIn, String checkOut, Room room, User user) {
		Reservation res = new Reservation();
//		res.setCheckIn(LocalDate.of(2019, 02, 10));
//		res.setCheckOut(LocalDate.of(2019, 02, 15));
		String str = "2015-03-15";
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    res.setCheckIn(LocalDate.parse(checkIn, formatter));
	    res.setCheckOut(LocalDate.parse(checkOut, formatter));
	    res.setRoom(room);
	    res.setUser(user);
	    return reservationRepository.save(res);
	}
	private User getUser(String firstName, String password, String login, String email, boolean activated, String role ) {
    	User user = new User();
    	user.setFirstName(firstName);
    	user.setPassword(passwordEncoder.encode(password));
        user.setLogin(login);
        user.setEmail(email);
        user.setActivated(activated);
        user.addAuthority(authorityRepository.getOne(role));
        return userRepository.save(user);
    }

    
    private Review getReview(int rating, String revText, User user) {
    	Review review = new Review();
    	review.setRating(rating);
    	review.setRevText(revText);
    	review.setUser(user);
    	return reviewRepository.save(review);
    }
//        
//        //load data
//        RfbLocation aleAndWitch = getRfbLocation("St Pete - Ale and the Witch", DayOfWeek.MONDAY.getValue());
//
//        rfbUser.setHomeLocation(aleAndWitch);
//        rfbUserRepository.save(rfbUser);
//
//        RfbEvent aleEvent = getRfbEvent(aleAndWitch);
//
//        getRfbEventAttendance(rfbUser, aleEvent);
//
//        RfbLocation ratc = getRfbLocation("St Pete - Right Around The Corner", DayOfWeek.TUESDAY.getValue());
//
//        RfbEvent ratcEvent = getRfbEvent(ratc);
//
//        getRfbEventAttendance(rfbUser, ratcEvent);
//
//        RfbLocation stPeteBrew = getRfbLocation("St Pete - St Pete Brewing", DayOfWeek.WEDNESDAY.getValue());
//
//        RfbEvent stPeteBrewEvent = getRfbEvent(stPeteBrew);
//
//        getRfbEventAttendance(rfbUser, stPeteBrewEvent);
//
//        RfbLocation yardOfAle = getRfbLocation("St Pete - Yard of Ale", DayOfWeek.THURSDAY.getValue());
//
//        RfbEvent yardOfAleEvent = getRfbEvent(yardOfAle);
//
//        getRfbEventAttendance(rfbUser, yardOfAleEvent);
//
//    }
//
//
//    private void getRfbEventAttendance(RfbUser rfbUser, RfbEvent rfbEvent) {
//        RfbEventAttendance rfbAttendance = new RfbEventAttendance();
//        rfbAttendance.setRfbEvent(rfbEvent);
//        rfbAttendance.setRfbUser(rfbUser);
//        rfbAttendance.setAttendanceDate(LocalDate.now());
//
//        System.out.println(rfbAttendance.toString());
//
//        rfbEventAttendanceRepository.save(rfbAttendance);
//        rfbEventRepository.save(rfbEvent);
//    }
//
//    private RfbEvent getRfbEvent(RfbLocation rfbLocation) {
//        RfbEvent rfbEvent = new RfbEvent();
//        rfbEvent.setEventCode(UUID.randomUUID().toString());
//        rfbEvent.setEventDate(LocalDate.now()); // will not be on assigned day...
//        rfbLocation.addRfbEvent(rfbEvent);
//        rfbLocationRepository.save(rfbLocation);
//        rfbEventRepository.save(rfbEvent);
//        return rfbEvent;
//    }
//
//    private RfbLocation getRfbLocation(String locationName, int value) {
//        RfbLocation rfbLocation = new RfbLocation();
//        rfbLocation.setLocationName(locationName);
//        rfbLocation.setRunDayOfWeek(value);
//        rfbLocationRepository.save(rfbLocation);
//        return rfbLocation;
//    }
}