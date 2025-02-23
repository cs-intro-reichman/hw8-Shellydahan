/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);//gg
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }
    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        //// Replace the following statement with your code
        for (int i = 0; i < userCount; i++){
            if (this.users[i]!=null && this.users[i].getName().equalsIgnoreCase(name)){
                return users[i];
            }
        }
        return null;
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
        //// Replace the following statement with your code
        for (int i = 0; i < userCount; i++) {
            if (this.users[i] != null && this.users[i].getName().equalsIgnoreCase(name)) {
                return false; 
            }
        }
        for (int i = 0; i < this.users.length; i++) {
            if (this.users[i] == null) {
                this.users[i] = new User(name); 
                userCount++; 
                return true;
            }
        }
        return false;
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
        //// Replace the following statement with your code
        if (name1 == null || name2 == null || name1.equalsIgnoreCase(name2)) {
            return false; 
        }
    
        User u1 = null;
        User u2 = null;
        for (int i = 0; i < userCount; i++){
            if (this.users[i] != null) {
                if (this.users[i].getName().equalsIgnoreCase(name1)) {
                    u1 = this.users[i];
                }
                if (this.users[i].getName().equalsIgnoreCase(name2)) {
                    u2 = this.users[i];
                }
            }
         
            if (u1 != null && u2 != null) {
                break;
            }
        }
    
        if (u1 == null || u2 == null){
            return false;
        }
    
        return u1.addFollowee(name2);
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        //// Replace the following statement with your code
        User u = getUser(name);
        if (u == null){
            return null; 
        }
        User mostRecommended = null;
        int maxMutual = 0;
        for (int i = 0; i < userCount; i++){
            User rec = this.users[i];
            if (rec == null || rec == u || u.follows(rec.getName())) {
                continue;
            }
            int mutualCount = rec.countMutual(u);
            if (mutualCount > maxMutual){
                maxMutual = mutualCount;
                mostRecommended = rec;
            }
        }
        if (mostRecommended != null){
            return mostRecommended.getName();
        } 
        else {
            return null;
        }  
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        //// Replace the following statement with your code
        if (userCount == 0) {
            return null; 
        }
        
        String pop = null;
        int max = 0;
    
        for (int i = 0; i < userCount; i++) {
            String currentUserName = this.users[i].getName();
            int currentFollowCount = followeeCount(currentUserName);
    
            if (currentFollowCount > max) {
                max = currentFollowCount;
                pop = currentUserName;
            }
        }
    
        return pop;
    
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        //// Replace the following statement with your code
        int appear = 0; 
        for (int i = 0; i < userCount; i++){
            if (this.users[i] != null){
                String[] follows = this.users[i].getfFollows();
                if (follows != null) {
                    for (int j = 0; j < follows.length; j++){
                        if (follows[j] != null && follows[j].equalsIgnoreCase(name)){
                            appear++;
                            break; 
                        }
                    }
                }
            }
        }
        return appear;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
       //// Replace the following statement with your code
       String ans = "Network:";
       for (int i = 0; i < userCount; i++) {
           ans = ans +  "\n" + users[i].toString();
       }
       return ans;
    }
}

