package org.agoncal.application.petstore.service;

import org.agoncal.application.petstore.domain.*;
import org.agoncal.application.petstore.util.Loggable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.sql.DataSourceDefinition;
import javax.annotation.sql.DataSourceDefinitions;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */

@Singleton
@Startup
@Loggable
@DataSourceDefinitions({
    @DataSourceDefinition (
        className="org.apache.derby.jdbc.EmbeddedDataSource",
        name="java:global/jdbc/applicationPetstoreDS",
        user="app",
        password="app",
        databaseName="applicationPetstoreDB",
        properties = {"connectionAttributes=;create=true"},
        url = "jdbc:derby:applicationPetstoreDB;create=true"
    ),
    @DataSourceDefinition (
        transactional = false,
        className="org.apache.derby.jdbc.EmbeddedDataSource",
        name="java:global/jdbc/applicationPetstoreDSNonJta",
        user="app",
        password="app",
        databaseName="applicationPetstoreDB",
        properties = {"connectionAttributes=;create=true"},
        url = "jdbc:derby:applicationPetstoreDB;create=true"
    )
})
public class DBPopulator {

    // ======================================
    // =             Attributes             =
    // ======================================

    Category fish;
    Category dog;
    Category reptile;
    Category cat;
    Category bird;
    Customer marc;
    Customer bill;
    Customer steve;
    Customer user;
    Customer admin;

    @Inject
    private CatalogService catalogService;

    @Inject
    private CustomerService customerService;

    // ======================================
    // =          Lifecycle Methods         =
    // ======================================

    @PostConstruct
    private void populateDB() {
        initCatalog();
        initCustomers();
    }

    @PreDestroy
    private void clearDB() {
        catalogService.removeCategory(fish);
        catalogService.removeCategory(dog);
        catalogService.removeCategory(reptile);
        catalogService.removeCategory(cat);
        catalogService.removeCategory(bird);
        customerService.removeCustomer(marc);
        customerService.removeCustomer(bill);
        customerService.removeCustomer(steve);
        customerService.removeCustomer(user);
        customerService.removeCustomer(admin);
    }

    // ======================================
    // =           Private Methods          =
    // ======================================

    private void initCatalog() {

        // Categories
        fish = new Category("Fish", "Any of numerous cold-blooded aquatic vertebrates characteristically having fins, gills, and a streamlined body");
        dog = new Category("Dogs", "A domesticated carnivorous mammal related to the foxes and wolves and raised in a wide variety of breeds");
        reptile = new Category("Reptiles", "Any of various cold-blooded, usually egg-laying vertebrates, such as a snake, lizard, crocodile, turtle");
        cat = new Category("Cats", " Small carnivorous mammal domesticated since early times as a catcher of rats and mice and as a pet and existing in several distinctive breeds and varieties");
        bird = new Category("Birds", "Any of the class Aves of warm-blooded, egg-laying, feathered vertebrates with forelimbs modified to form wings");

        // Products

        { // Anglefish
            Product product = new Product("Angelfish", "Saltwater fish from Australia", fish);
            product.addItem(new Item("Large", 10.00f, "fish1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum velit ante, malesuada porta condimentum eget, tristique id magna. Donec ac justo velit. Suspendisse potenti. Donec vulputate vulputate molestie. Quisque vitae arcu massa, dictum sodales leo. Sed feugiat elit vitae ante auctor ultrices. Duis auctor consectetur arcu id faucibus. Curabitur gravida."));
            product.addItem(new Item("Thootless", 10.00f, "fish1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur fringilla pharetra dignissim. In imperdiet, lacus a vehicula dignissim, ante ligula euismod leo, non lobortis orci quam a nisl. Aliquam risus eros, molestie sit amet interdum nec, convallis malesuada leo. Quisque bibendum facilisis erat eget tincidunt. Phasellus pharetra gravida purus. Maecenas."));
            fish.addProduct(product);
        }

        { // Tiger Shark
            Product product = new Product("Tiger Shark", "Saltwater fish from Australia", fish);
            product.addItem(new Item("Spotted", 12.00f, "fish4.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque dictum iaculis sapien, eu fermentum eros feugiat a. Pellentesque ultricies mauris orci. Mauris interdum hendrerit felis vel dignissim. Phasellus ac sem sit amet ante laoreet volutpat. Sed sagittis venenatis ullamcorper. Vivamus non mollis nunc. Etiam mauris odio, tristique sed porta in."));
            product.addItem(new Item("Spotless", 12.00f, "fish4.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In hendrerit ultricies bibendum. Vestibulum vitae dui porttitor nibh dignissim pretium eu at odio. Proin ac nibh eget erat ullamcorper consequat ac cursus est. Donec sollicitudin interdum elit sed gravida. Integer lacus lacus, gravida eget vehicula ac, sagittis et dui. In et."));
            fish.addProduct(product);
        }

        { // Fleetwood Mackerel
            Product product = new Product("Fleetwood Mackerel", "Star-crossed lover fish from London", fish);
            product.addItem(new Item("John McVie", 12.00f, "fish4.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque dictum iaculis sapien, eu fermentum eros feugiat a. Pellentesque ultricies mauris orci. Mauris interdum hendrerit felis vel dignissim. Phasellus ac sem sit amet ante laoreet volutpat. Sed sagittis venenatis ullamcorper. Vivamus non mollis nunc. Etiam mauris odio, tristique sed porta in."));
            product.addItem(new Item("Stevie Nicks", 12.00f, "fish4.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In hendrerit ultricies bibendum. Vestibulum vitae dui porttitor nibh dignissim pretium eu at odio. Proin ac nibh eget erat ullamcorper consequat ac cursus est. Donec sollicitudin interdum elit sed gravida. Integer lacus lacus, gravida eget vehicula ac, sagittis et dui. In et."));
            product.addItem(new Item("Lindsey Buckingham", 12.00f, "fish4.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In hendrerit ultricies bibendum. Vestibulum vitae dui porttitor nibh dignissim pretium eu at odio. Proin ac nibh eget erat ullamcorper consequat ac cursus est. Donec sollicitudin interdum elit sed gravida. Integer lacus lacus, gravida eget vehicula ac, sagittis et dui. In et."));
            product.addItem(new Item("Mick Fleetwood", 12.00f, "fish4.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In hendrerit ultricies bibendum. Vestibulum vitae dui porttitor nibh dignissim pretium eu at odio. Proin ac nibh eget erat ullamcorper consequat ac cursus est. Donec sollicitudin interdum elit sed gravida. Integer lacus lacus, gravida eget vehicula ac, sagittis et dui. In et."));
            fish.addProduct(product);
        }

        { // Koi
            Product product = new Product("Koi", "Freshwater fish from Japan", fish);
            product.addItem(new Item("Male Adult", 12.00f, "fish3.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi consectetur, ligula eu malesuada tempus, risus tellus varius ligula, id auctor magna tellus quis dui. Integer ut neque ut libero aliquet hendrerit. Maecenas bibendum, magna sed vulputate tempor, tortor neque consequat nunc, id consectetur neque odio eget augue. Ut consectetur, nisl."));
            product.addItem(new Item("Female Adult", 12.00f, "fish3.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec et porta eros. Aliquam neque arcu, sodales eget rutrum a, luctus sit amet sem. Vestibulum ultricies dictum mi, eu sagittis lacus ultrices sit amet. Mauris nec interdum ipsum. Maecenas semper, magna sit amet commodo tempus, purus lectus pretium dui, sit amet."));
            fish.addProduct(product);
        }

        { // Fishbone
            Product product = new Product("Fishbone", "Never-hit-mainstream fish from Los Angeles", fish);
            product.addItem(new Item("Angelo Moore", 12.00f, "fish3.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec et porta eros. Aliquam neque arcu, sodales eget rutrum a, luctus sit amet sem. Vestibulum ultricies dictum mi, eu sagittis lacus ultrices sit amet. Mauris nec interdum ipsum. Maecenas semper, magna sit amet commodo tempus, purus lectus pretium dui, sit amet."));
            product.addItem(new Item("John Norwood Fisher", 12.00f, "fish3.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec et porta eros. Aliquam neque arcu, sodales eget rutrum a, luctus sit amet sem. Vestibulum ultricies dictum mi, eu sagittis lacus ultrices sit amet. Mauris nec interdum ipsum. Maecenas semper, magna sit amet commodo tempus, purus lectus pretium dui, sit amet."));
            product.addItem(new Item("Walter A. Kibby II", 12.00f, "fish3.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec et porta eros. Aliquam neque arcu, sodales eget rutrum a, luctus sit amet sem. Vestibulum ultricies dictum mi, eu sagittis lacus ultrices sit amet. Mauris nec interdum ipsum. Maecenas semper, magna sit amet commodo tempus, purus lectus pretium dui, sit amet."));
            product.addItem(new Item("John Steward", 12.00f, "fish3.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec et porta eros. Aliquam neque arcu, sodales eget rutrum a, luctus sit amet sem. Vestibulum ultricies dictum mi, eu sagittis lacus ultrices sit amet. Mauris nec interdum ipsum. Maecenas semper, magna sit amet commodo tempus, purus lectus pretium dui, sit amet."));
            product.addItem(new Item("Rocky George", 12.00f, "fish3.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec et porta eros. Aliquam neque arcu, sodales eget rutrum a, luctus sit amet sem. Vestibulum ultricies dictum mi, eu sagittis lacus ultrices sit amet. Mauris nec interdum ipsum. Maecenas semper, magna sit amet commodo tempus, purus lectus pretium dui, sit amet."));
            product.addItem(new Item("Jay Armant", 12.00f, "fish3.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec et porta eros. Aliquam neque arcu, sodales eget rutrum a, luctus sit amet sem. Vestibulum ultricies dictum mi, eu sagittis lacus ultrices sit amet. Mauris nec interdum ipsum. Maecenas semper, magna sit amet commodo tempus, purus lectus pretium dui, sit amet."));
            fish.addProduct(product);
        }

        { // Goldfish
            Product product = new Product("Goldfish", "Freshwater fish from China", fish);
            product.addItem(new Item("Male Puppy", 12.00f, "fish2.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean ac nunc mauris. Proin augue sem, imperdiet quis imperdiet vitae, egestas vitae quam. Nam id lectus nisi. In hac habitasse platea dictumst. Proin ullamcorper eros non diam accumsan ornare. Fusce posuere, nulla vel tempor molestie, lectus dui aliquet orci, in volutpat."));
            product.addItem(new Item("Female Puppy", 12.00f, "fish2.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc pretium ornare est ullamcorper porta. Nullam eleifend tincidunt justo nec ultrices. In vehicula pharetra turpis, nec consequat sapien tempus sit amet. Donec quis urna in odio luctus rhoncus. In metus lorem, ultricies vel vestibulum non, laoreet ac neque. Duis posuere, tortor."));
            fish.addProduct(product);
        }

        { // Great white
            Product product = new Product("Great White", "Once-bitten-twice-forgotten fish from Los Angeles", fish);
            final String imagePath = "fish2.jpg";
            final String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean ac nunc mauris. Proin augue sem, imperdiet quis imperdiet vitae, egestas vitae quam. Nam id lectus nisi. In hac habitasse platea dictumst. Proin ullamcorper eros non diam accumsan ornare. Fusce posuere, nulla vel tempor molestie, lectus dui aliquet orci, in volutpat.";
            product.addItem(new Item("Lorne Black", 12.00f, imagePath, product, description));
            product.addItem(new Item("Mark Kendall", 12.00f, imagePath, product, description));
            product.addItem(new Item("Jack Russell", 12.00f, imagePath, product, description));
            product.addItem(new Item("Audie Desbrow", 12.00f, imagePath, product, description));
            fish.addProduct(product);
        }

        { // Bulldog
            Product product = new Product("Bulldog", "Friendly dog from England", dog);
            product.addItem(new Item("Spotless Male Puppy", 22.00f, "dog1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce et lorem vel tellus aliquet pretium ut nec libero. Cras euismod tincidunt rutrum. Suspendisse nisl justo, vestibulum et commodo vel, ultricies placerat quam. Sed nisi orci, rhoncus ac accumsan eget, pretium ac purus. Nam et scelerisque mi. Vivamus luctus, massa eget."));
            product.addItem(new Item("Spotless Female Puppy", 22.00f, "dog1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam rhoncus arcu sed sapien interdum porttitor. Nulla nunc magna, egestas sed laoreet nec, congue et felis. Donec rhoncus, est vitae tincidunt posuere, dolor nunc fermentum orci, ut varius velit ipsum a massa. Pellentesque habitant morbi tristique senectus et netus et malesuada."));
            dog.addProduct(product);
        }

        { // Poodle
            Product product = new Product("Poodle", "Cute dog from France", dog);
            product.addItem(new Item("Spotted Male Puppy", 32.00f, "dog2.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis ipsum erat, tincidunt sit amet lacinia non, vestibulum elementum odio. Donec id lacus ante, id bibendum est. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Etiam eu suscipit mauris. Vivamus dolor diam, pulvinar a consectetur at."));
            product.addItem(new Item("Spotted Female Puppy", 32.00f, "dog2.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi nec justo orci, in faucibus lectus. Proin feugiat faucibus pellentesque. Etiam nec dolor justo, non egestas nisl. Etiam convallis orci nec felis pretium malesuada. Maecenas nec tortor erat. Cras accumsan eros sit amet nibh fringilla molestie. Suspendisse potenti. Nulla vulputate neque."));
            dog.addProduct(product);
        }

        { // Dalmation
            Product product = new Product("Dalmation", "Great dog for a fire station", dog);
            product.addItem(new Item("Tailed", 62.00f, "dog3.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ante massa, semper non tempor at, faucibus nec est. Aliquam aliquet, tortor ut egestas blandit, nisi urna elementum lectus, a porta dolor leo quis massa. Aliquam erat volutpat. Fusce sed eros et enim varius consequat. Nam molestie, neque quis commodo rhoncus."));
            product.addItem(new Item("Tailless", 62.00f, "dog3.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus ac adipiscing nulla. Proin risus lectus, convallis eu sagittis scelerisque, fringilla ut odio. Suspendisse ultrices ullamcorper adipiscing. Proin ac suscipit tellus. Vivamus tempus nibh interdum ipsum ullamcorper at suscipit nibh mattis. Vivamus elementum volutpat ipsum eu tempus. Proin velit ligula, fringilla."));
            dog.addProduct(product);
        }

        { // Three Dog Night
            Product product = new Product("Three Dog Night", "Mama told me not to get one", dog);
            product.addItem(new Item("Cory Wells", 12.00f, "dog3.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ante massa, semper non tempor at, faucibus nec est. Aliquam aliquet, tortor ut egestas blandit, nisi urna elementum lectus, a porta dolor leo quis massa. Aliquam erat volutpat. Fusce sed eros et enim varius consequat. Nam molestie, neque quis commodo rhoncus."));
            product.addItem(new Item("Danny Hutton", 12.00f, "dog3.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ante massa, semper non tempor at, faucibus nec est. Aliquam aliquet, tortor ut egestas blandit, nisi urna elementum lectus, a porta dolor leo quis massa. Aliquam erat volutpat. Fusce sed eros et enim varius consequat. Nam molestie, neque quis commodo rhoncus."));
            product.addItem(new Item("Jimmy Greenspoon", 12.00f, "dog3.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ante massa, semper non tempor at, faucibus nec est. Aliquam aliquet, tortor ut egestas blandit, nisi urna elementum lectus, a porta dolor leo quis massa. Aliquam erat volutpat. Fusce sed eros et enim varius consequat. Nam molestie, neque quis commodo rhoncus."));
            product.addItem(new Item("Michael Allsup", 12.00f, "dog3.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ante massa, semper non tempor at, faucibus nec est. Aliquam aliquet, tortor ut egestas blandit, nisi urna elementum lectus, a porta dolor leo quis massa. Aliquam erat volutpat. Fusce sed eros et enim varius consequat. Nam molestie, neque quis commodo rhoncus."));
            product.addItem(new Item("Paul Kingery", 12.00f, "dog3.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ante massa, semper non tempor at, faucibus nec est. Aliquam aliquet, tortor ut egestas blandit, nisi urna elementum lectus, a porta dolor leo quis massa. Aliquam erat volutpat. Fusce sed eros et enim varius consequat. Nam molestie, neque quis commodo rhoncus."));
            product.addItem(new Item("Pat Bautz", 12.00f, "dog3.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ante massa, semper non tempor at, faucibus nec est. Aliquam aliquet, tortor ut egestas blandit, nisi urna elementum lectus, a porta dolor leo quis massa. Aliquam erat volutpat. Fusce sed eros et enim varius consequat. Nam molestie, neque quis commodo rhoncus."));
            dog.addProduct(product);
        }

        { // Golden Retriever
            Product product = new Product("Golden Retriever", "Great family dog", dog);
            product.addItem(new Item("Tailed", 82.00f, "dog4.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin sit amet velit id augue pellentesque tempor suscipit eu nisi. Nulla facilisi. Sed ultrices lectus in ligula viverra lacinia. Quisque et leo nisl. Suspendisse potenti. Donec semper malesuada ullamcorper. Vivamus fringilla nunc eget tellus condimentum ut dictum nisi euismod. Pellentesque habitant."));
            product.addItem(new Item("Tailless", 82.00f, "dog4.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In eget justo odio. Phasellus suscipit auctor lectus eget luctus. Nam ultricies auctor augue vel feugiat. Nulla odio lectus, volutpat sit amet vestibulum id, convallis sit amet tellus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Quisque."));
            dog.addProduct(product);
        }

        { // Labrador Retriever
            Product product = new Product("Labrador Retriever", "Great hunting dog", dog);
            product.addItem(new Item("Tailed", 100.00f, "dog5.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lobortis volutpat nunc, in sodales felis condimentum a. Quisque quis neque commodo elit consequat porttitor. Integer nec scelerisque nisi. Aliquam velit lorem, egestas sit amet sodales sit amet, gravida ut lorem. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices."));
            product.addItem(new Item("Tailless", 100.00f, "dog5.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin tortor mauris, sodales sodales pretium vitae, egestas eget mi. Ut hendrerit, libero et feugiat tristique, ligula nunc varius sem, non tristique mi ante a turpis. Suspendisse potenti. Nunc fringilla imperdiet nibh, eu sodales nisl pellentesque eu. Curabitur dictum vestibulum elit ut."));
            dog.addProduct(product);
        }

        { // Steppenwolf
            Product product = new Product("Steppenwolf", "Born to be compiled", dog);
            product.addItem(new Item("John Kay", 12.00f, "dog5.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lobortis volutpat nunc, in sodales felis condimentum a. Quisque quis neque commodo elit consequat porttitor. Integer nec scelerisque nisi. Aliquam velit lorem, egestas sit amet sodales sit amet, gravida ut lorem. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices."));
            product.addItem(new Item("Michael Wilk", 12.00f, "dog5.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lobortis volutpat nunc, in sodales felis condimentum a. Quisque quis neque commodo elit consequat porttitor. Integer nec scelerisque nisi. Aliquam velit lorem, egestas sit amet sodales sit amet, gravida ut lorem. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices."));
            product.addItem(new Item("Gary Link", 12.00f, "dog5.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lobortis volutpat nunc, in sodales felis condimentum a. Quisque quis neque commodo elit consequat porttitor. Integer nec scelerisque nisi. Aliquam velit lorem, egestas sit amet sodales sit amet, gravida ut lorem. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices."));
            product.addItem(new Item("Ron Hurst", 12.00f, "dog5.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lobortis volutpat nunc, in sodales felis condimentum a. Quisque quis neque commodo elit consequat porttitor. Integer nec scelerisque nisi. Aliquam velit lorem, egestas sit amet sodales sit amet, gravida ut lorem. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices."));
            product.addItem(new Item("Danny Johnson", 12.00f, "dog5.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lobortis volutpat nunc, in sodales felis condimentum a. Quisque quis neque commodo elit consequat porttitor. Integer nec scelerisque nisi. Aliquam velit lorem, egestas sit amet sodales sit amet, gravida ut lorem. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices."));
            dog.addProduct(product);
        }

        { // Chihuahua
            Product product = new Product("Chihuahua", "Great companion dog", dog);
            product.addItem(new Item("Male Adult", 100.00f, "dog6.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere porta risus, a bibendum enim pellentesque sit amet. Mauris imperdiet suscipit lectus, sed molestie orci posuere a. Fusce eleifend interdum nisi, nec vulputate velit rutrum ut. Nulla turpis ligula, fermentum ac tincidunt at, porttitor sit amet sem. Curabitur eget eros."));
            product.addItem(new Item("Female Adult", 100.00f, "dog6.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras in diam sapien. Etiam sed dapibus velit. Phasellus gravida egestas congue. Etiam nec nunc non arcu facilisis ultrices. Curabitur et diam sed neque facilisis dignissim. Vestibulum accumsan viverra nunc, ac tincidunt nisi placerat sit amet. Nulla ac pellentesque justo. Aliquam pellentesque."));
            dog.addProduct(product);
        }

        { // Snoop Dog
            Product product = new Product("Snoop Dogg", "Avilable with prescription", dog);
            product.addItem(new Item("Snoop Doggy Dogg", 1000.00f, "dog6.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere porta risus, a bibendum enim pellentesque sit amet. Mauris imperdiet suscipit lectus, sed molestie orci posuere a. Fusce eleifend interdum nisi, nec vulputate velit rutrum ut. Nulla turpis ligula, fermentum ac tincidunt at, porttitor sit amet sem. Curabitur eget eros."));
            dog.addProduct(product);
        }

        { // Rattlesnake
            Product product = new Product("Rattlesnake", "Doubles as a watch dog", reptile);
            product.addItem(new Item("Female Adult", 20.00f, "reptile1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lobortis ante et nunc scelerisque aliquet. Phasellus sed auctor purus. Cras tempus lacus eget felis viverra scelerisque. Sed ac tellus vitae nisl vehicula feugiat ac vitae dolor. Duis interdum lorem quis risus ullamcorper id cursus magna pharetra. Sed et nisi odio."));
            product.addItem(new Item("Male Adult", 20.00f, "reptile1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris pharetra tempus vulputate. Proin at nibh at felis feugiat fringilla. Fusce suscipit malesuada urna posuere suscipit. Integer non quam orci, vel adipiscing odio. Aenean at turpis nisi, a ullamcorper massa. Integer consectetur libero a lorem blandit pretium. Curabitur a sodales justo."));
            reptile.addProduct(product);
        }

        { // Whitesnake
            Product product = new Product("Whitesnake", "Tawny Kitaen not included", reptile);
            product.addItem(new Item("David Coverdale", 12.00f, "reptile1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lobortis ante et nunc scelerisque aliquet. Phasellus sed auctor purus. Cras tempus lacus eget felis viverra scelerisque. Sed ac tellus vitae nisl vehicula feugiat ac vitae dolor. Duis interdum lorem quis risus ullamcorper id cursus magna pharetra. Sed et nisi odio."));
            product.addItem(new Item("Tommy Aldridge", 12.00f, "reptile1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lobortis ante et nunc scelerisque aliquet. Phasellus sed auctor purus. Cras tempus lacus eget felis viverra scelerisque. Sed ac tellus vitae nisl vehicula feugiat ac vitae dolor. Duis interdum lorem quis risus ullamcorper id cursus magna pharetra. Sed et nisi odio."));
            product.addItem(new Item("Doug Aldrich", 12.00f, "reptile1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lobortis ante et nunc scelerisque aliquet. Phasellus sed auctor purus. Cras tempus lacus eget felis viverra scelerisque. Sed ac tellus vitae nisl vehicula feugiat ac vitae dolor. Duis interdum lorem quis risus ullamcorper id cursus magna pharetra. Sed et nisi odio."));
            product.addItem(new Item("Reb Beach", 12.00f, "reptile1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lobortis ante et nunc scelerisque aliquet. Phasellus sed auctor purus. Cras tempus lacus eget felis viverra scelerisque. Sed ac tellus vitae nisl vehicula feugiat ac vitae dolor. Duis interdum lorem quis risus ullamcorper id cursus magna pharetra. Sed et nisi odio."));
            product.addItem(new Item("Michael Devin", 12.00f, "reptile1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lobortis ante et nunc scelerisque aliquet. Phasellus sed auctor purus. Cras tempus lacus eget felis viverra scelerisque. Sed ac tellus vitae nisl vehicula feugiat ac vitae dolor. Duis interdum lorem quis risus ullamcorper id cursus magna pharetra. Sed et nisi odio."));
            product.addItem(new Item("Brian Ruedy", 12.00f, "reptile1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent lobortis ante et nunc scelerisque aliquet. Phasellus sed auctor purus. Cras tempus lacus eget felis viverra scelerisque. Sed ac tellus vitae nisl vehicula feugiat ac vitae dolor. Duis interdum lorem quis risus ullamcorper id cursus magna pharetra. Sed et nisi odio."));
            reptile.addProduct(product);
        }

        { // Iguana
            Product product = new Product("Iguana", "Friendly green friend", reptile);
            product.addItem(new Item("Female Adult", 150.00f, "lizard1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin dictum, nisi vitae fringilla ultrices, est ipsum faucibus ipsum, sit amet dapibus erat ipsum et arcu. Sed euismod, mauris suscipit placerat semper, tortor magna cursus nulla, id elementum dui dolor sit amet nunc. Pellentesque a interdum lectus. Mauris in augue eu."));
            product.addItem(new Item("Male Adult", 160.00f, "lizard1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus at dapibus arcu. Nunc at dui sem, in fringilla velit. Suspendisse mauris felis, molestie scelerisque viverra sit amet, dapibus eu diam. Curabitur egestas lectus et ligula pharetra in sollicitudin neque tristique. Nunc suscipit scelerisque nunc, vitae consectetur justo sodales ullamcorper. Nulla."));
            reptile.addProduct(product);
        }

        { // Manx
            Product product = new Product("Manx", "Great for reducing mouse populations", cat);
            product.addItem(new Item("Male Adult", 120.00f, "cat1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed convallis scelerisque urna. Sed id nunc quis nisl scelerisque scelerisque sit amet id lorem. Sed rutrum arcu sed sem semper id eleifend nulla feugiat. Praesent faucibus dignissim lectus tincidunt lacinia. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per."));
            product.addItem(new Item("Female Adult", 120.00f, "cat1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus viverra nunc vitae libero ultricies lobortis. Duis magna nunc, tincidunt sit amet sagittis et, lobortis volutpat risus. Sed gravida turpis sit amet arcu tincidunt convallis. Nunc vulputate commodo mi non blandit. Etiam eu libero id libero aliquet pretium. Lorem ipsum dolor."));
            cat.addProduct(product);
        }

        { // Def Leppard
            Product product = new Product("Def Leppard", "Costs an arm and a leg... well, at least an arm", cat);
            product.addItem(new Item("Rick Savage", 12.00f, "cat1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed convallis scelerisque urna. Sed id nunc quis nisl scelerisque scelerisque sit amet id lorem. Sed rutrum arcu sed sem semper id eleifend nulla feugiat. Praesent faucibus dignissim lectus tincidunt lacinia. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per."));
            product.addItem(new Item("Joe Elliott", 12.00f, "cat1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed convallis scelerisque urna. Sed id nunc quis nisl scelerisque scelerisque sit amet id lorem. Sed rutrum arcu sed sem semper id eleifend nulla feugiat. Praesent faucibus dignissim lectus tincidunt lacinia. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per."));
            product.addItem(new Item("Rick Allen", 12.00f, "cat1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed convallis scelerisque urna. Sed id nunc quis nisl scelerisque scelerisque sit amet id lorem. Sed rutrum arcu sed sem semper id eleifend nulla feugiat. Praesent faucibus dignissim lectus tincidunt lacinia. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per."));
            product.addItem(new Item("Phil Collen", 12.00f, "cat1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed convallis scelerisque urna. Sed id nunc quis nisl scelerisque scelerisque sit amet id lorem. Sed rutrum arcu sed sem semper id eleifend nulla feugiat. Praesent faucibus dignissim lectus tincidunt lacinia. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per."));
            product.addItem(new Item("Vivian Campbell", 12.00f, "cat1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed convallis scelerisque urna. Sed id nunc quis nisl scelerisque scelerisque sit amet id lorem. Sed rutrum arcu sed sem semper id eleifend nulla feugiat. Praesent faucibus dignissim lectus tincidunt lacinia. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per."));
            cat.addProduct(product);
        }

        { // Persian
            Product product = new Product("Persian", "Friendly house cat, doubles as a princess", cat);
            product.addItem(new Item("Male Adult", 70.00f, "cat2.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut sed est in tortor pharetra fermentum. Pellentesque nulla augue, venenatis ut viverra vel, dignissim sit amet ante. Aliquam erat volutpat. Aenean lectus odio, blandit aliquam sollicitudin a, pulvinar a felis. Phasellus vitae libero et lacus volutpat tristique. Aliquam tortor lacus, pulvinar."));
            product.addItem(new Item("Female Adult", 90.00f, "cat2.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam fringilla iaculis nunc et hendrerit. Curabitur malesuada felis non velit ultrices lacinia. Vivamus hendrerit tortor et tortor faucibus vehicula. Pellentesque pellentesque, quam at viverra tristique, lacus nibh euismod erat, vel vestibulum purus turpis eget nisi. Donec suscipit ligula tortor, a suscipit."));
            cat.addProduct(product);
        }

        { // White Lion
            Product product = new Product("White Lion", "Dutch can cat of rock", cat);
            product.addItem(new Item("Mike Tramp", 12.00f, "cat2.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut sed est in tortor pharetra fermentum. Pellentesque nulla augue, venenatis ut viverra vel, dignissim sit amet ante. Aliquam erat volutpat. Aenean lectus odio, blandit aliquam sollicitudin a, pulvinar a felis. Phasellus vitae libero et lacus volutpat tristique. Aliquam tortor lacus, pulvinar."));
            product.addItem(new Item("Jamie Law", 12.00f, "cat2.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut sed est in tortor pharetra fermentum. Pellentesque nulla augue, venenatis ut viverra vel, dignissim sit amet ante. Aliquam erat volutpat. Aenean lectus odio, blandit aliquam sollicitudin a, pulvinar a felis. Phasellus vitae libero et lacus volutpat tristique. Aliquam tortor lacus, pulvinar."));
            product.addItem(new Item("Troy Patrick Farrell", 12.00f, "cat2.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut sed est in tortor pharetra fermentum. Pellentesque nulla augue, venenatis ut viverra vel, dignissim sit amet ante. Aliquam erat volutpat. Aenean lectus odio, blandit aliquam sollicitudin a, pulvinar a felis. Phasellus vitae libero et lacus volutpat tristique. Aliquam tortor lacus, pulvinar."));
            product.addItem(new Item("Claus Langeskov", 12.00f, "cat2.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut sed est in tortor pharetra fermentum. Pellentesque nulla augue, venenatis ut viverra vel, dignissim sit amet ante. Aliquam erat volutpat. Aenean lectus odio, blandit aliquam sollicitudin a, pulvinar a felis. Phasellus vitae libero et lacus volutpat tristique. Aliquam tortor lacus, pulvinar."));
            product.addItem(new Item("Henning Wanner", 12.00f, "cat2.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut sed est in tortor pharetra fermentum. Pellentesque nulla augue, venenatis ut viverra vel, dignissim sit amet ante. Aliquam erat volutpat. Aenean lectus odio, blandit aliquam sollicitudin a, pulvinar a felis. Phasellus vitae libero et lacus volutpat tristique. Aliquam tortor lacus, pulvinar."));
            cat.addProduct(product);
        }

        { // Amazon Parrot
            Product product = new Product("Amazon Parrot", "Great companion for up to 75 years", bird);
            product.addItem(new Item("Male Adult", 120.00f, "bird2.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In justo ligula, volutpat ut adipiscing sed, lobortis vel lacus. Etiam commodo aliquet libero, sit amet pretium risus scelerisque luctus. Suspendisse sit amet nulla nibh, in mollis risus. Curabitur convallis mattis felis, non malesuada justo pretium sed. Nam vestibulum, urna in consequat."));
            product.addItem(new Item("Female Adult", 120.00f, "bird2.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse vitae turpis ut erat hendrerit sollicitudin. Curabitur auctor neque a enim scelerisque mattis. Mauris in mi nibh, et placerat lorem. Nunc semper, quam vitae semper condimentum, odio arcu sagittis ligula, eu posuere arcu nibh a quam. Aliquam porta dictum eros auctor."));
            bird.addProduct(product);
        }

        { // The Eagles
            Product product = new Product("The Eagles", "Great companion for up to 75 years, give or take a few breakups", bird);
            product.addItem(new Item("Glenn Frey", 12.00f, "bird2.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In justo ligula, volutpat ut adipiscing sed, lobortis vel lacus. Etiam commodo aliquet libero, sit amet pretium risus scelerisque luctus. Suspendisse sit amet nulla nibh, in mollis risus. Curabitur convallis mattis felis, non malesuada justo pretium sed. Nam vestibulum, urna in consequat."));
            product.addItem(new Item("Don Henley", 12.00f, "bird2.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In justo ligula, volutpat ut adipiscing sed, lobortis vel lacus. Etiam commodo aliquet libero, sit amet pretium risus scelerisque luctus. Suspendisse sit amet nulla nibh, in mollis risus. Curabitur convallis mattis felis, non malesuada justo pretium sed. Nam vestibulum, urna in consequat."));
            product.addItem(new Item("Joe Walsh", 12.00f, "bird2.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In justo ligula, volutpat ut adipiscing sed, lobortis vel lacus. Etiam commodo aliquet libero, sit amet pretium risus scelerisque luctus. Suspendisse sit amet nulla nibh, in mollis risus. Curabitur convallis mattis felis, non malesuada justo pretium sed. Nam vestibulum, urna in consequat."));
            product.addItem(new Item("Timothy B. Schmit ", 12.00f, "bird2.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In justo ligula, volutpat ut adipiscing sed, lobortis vel lacus. Etiam commodo aliquet libero, sit amet pretium risus scelerisque luctus. Suspendisse sit amet nulla nibh, in mollis risus. Curabitur convallis mattis felis, non malesuada justo pretium sed. Nam vestibulum, urna in consequat."));
            bird.addProduct(product);
        }

        { // Yarbirds
            Product product = new Product("Yardbirds", "Warning: Pet may leave home and become incredibly famous", bird);
            product.addItem(new Item("Eric Clapton", 12.00f, "bird1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum quis lectus sit amet augue mattis malesuada. Maecenas justo justo, auctor sed consectetur et, pulvinar et diam. Nam felis mi, auctor ornare accumsan sed, pharetra nec arcu. Aliquam tincidunt nisi feugiat dui commodo dapibus. Nullam eget augue odio. Duis mauris nibh."));
            product.addItem(new Item("Jeff Beck", 12.00f, "bird1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum quis lectus sit amet augue mattis malesuada. Maecenas justo justo, auctor sed consectetur et, pulvinar et diam. Nam felis mi, auctor ornare accumsan sed, pharetra nec arcu. Aliquam tincidunt nisi feugiat dui commodo dapibus. Nullam eget augue odio. Duis mauris nibh."));
            product.addItem(new Item("Jimmy Page", 12.00f, "bird1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum quis lectus sit amet augue mattis malesuada. Maecenas justo justo, auctor sed consectetur et, pulvinar et diam. Nam felis mi, auctor ornare accumsan sed, pharetra nec arcu. Aliquam tincidunt nisi feugiat dui commodo dapibus. Nullam eget augue odio. Duis mauris nibh."));
            product.addItem(new Item("Chris Dreja", 12.00f, "bird1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum quis lectus sit amet augue mattis malesuada. Maecenas justo justo, auctor sed consectetur et, pulvinar et diam. Nam felis mi, auctor ornare accumsan sed, pharetra nec arcu. Aliquam tincidunt nisi feugiat dui commodo dapibus. Nullam eget augue odio. Duis mauris nibh."));
            product.addItem(new Item("Keith Relf", 12.00f, "bird1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum quis lectus sit amet augue mattis malesuada. Maecenas justo justo, auctor sed consectetur et, pulvinar et diam. Nam felis mi, auctor ornare accumsan sed, pharetra nec arcu. Aliquam tincidunt nisi feugiat dui commodo dapibus. Nullam eget augue odio. Duis mauris nibh."));
            product.addItem(new Item("Jim McCarty", 12.00f, "bird1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum quis lectus sit amet augue mattis malesuada. Maecenas justo justo, auctor sed consectetur et, pulvinar et diam. Nam felis mi, auctor ornare accumsan sed, pharetra nec arcu. Aliquam tincidunt nisi feugiat dui commodo dapibus. Nullam eget augue odio. Duis mauris nibh."));
            bird.addProduct(product);
        }

        { // Finch
            Product product = new Product("Finch", "Great stress reliever", bird);
            product.addItem(new Item("Male Adult", 75.00f, "bird1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum quis lectus sit amet augue mattis malesuada. Maecenas justo justo, auctor sed consectetur et, pulvinar et diam. Nam felis mi, auctor ornare accumsan sed, pharetra nec arcu. Aliquam tincidunt nisi feugiat dui commodo dapibus. Nullam eget augue odio. Duis mauris nibh."));
            product.addItem(new Item("Female Adult", 80.00f, "bird1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus dignissim vehicula tellus. Vestibulum id diam eros. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nam sit amet sem at ligula pretium fermentum. Suspendisse potenti. Phasellus rhoncus consequat augue, ac feugiat felis gravida nec. Aliquam at."));
            bird.addProduct(product);
        }

        catalogService.createCategory(fish);
        catalogService.createCategory(dog);
        catalogService.createCategory(reptile);
        catalogService.createCategory(cat);
        catalogService.createCategory(bird);
    }

    private void initCustomers() {
        marc = new Customer("Marc", "Fleury", "marc", "marc", "marc@jboss.org", new Address("65 Ritherdon Road", "Los Angeles", "56421", "USA"));
        bill = new Customer("Bill", "Gates", "bill", "bill", "bill.gates@microsoft.com", new Address("27 West Side", "Alhabama", "8401", "USA"));
        steve = new Customer("Steve", "Jobs", "jobs", "jobs", "steve.jobs@apple.com", new Address("154 Star Boulevard", "San Francisco", "5455", "USA"));
        user = new Customer("User", "User", "user", "user", "user@petstore.org", new Address("Petstore", "Land", "666", "Nowhere"));
        admin = new Customer("Admin", "Admin", "admin", "admin", "admin@petstore.org", new Address("Petstore", "Land", "666", "Nowhere"));

        customerService.createCustomer(marc);
        customerService.createCustomer(bill);
        customerService.createCustomer(steve);
        customerService.createCustomer(user);
        customerService.createCustomer(admin);
    }

}
