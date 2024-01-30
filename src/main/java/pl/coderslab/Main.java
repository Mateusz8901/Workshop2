package pl.coderslab;

public class Main {
    public static void main(String[] args) {
        User user1 = new User();
        user1.setUsername("exampleUser");
        user1.setEmail("example@example.com");

        String password = "password123";
        String hashedPassword = PasswordHelper.hashPassword(password);
        user1.setPassword(hashedPassword);

        UserDao userDao = new UserDao();

        User createdUser = userDao.create(user1);

        System.out.println("Id of the created user: " + createdUser.getId());

        User foundUser = userDao.read(createdUser.getId());
        System.out.println("Username of the found user: " + foundUser.getUsername());

        foundUser.setUsername("newUsername");
        userDao.update(foundUser);

        userDao.delete(foundUser.getId());

        User[] allUsers = userDao.findAll();
        for (User u : allUsers) {
            System.out.println(u.getUsername());
        }
    }
}