//
//  ViewController.m
//  HitList
//
//  Created by AlinaComp on 1/21/16.
//  Copyright © 2016 Razerware. All rights reserved.
//

#import "ViewController.h"
#import <CoreData/CoreData.h>
#import "AppDelegate.h"

@interface ViewController ()

@property NSMutableArray* people;

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    self.title = @"\"The List\"";
    [self.tableView registerClass: UITableViewCell.self forCellReuseIdentifier: @"Cell"];
    self.people = [NSMutableArray new];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    AppDelegate* appDelegate =
    (AppDelegate*)[[UIApplication sharedApplication] delegate];
    NSManagedObjectContext* managedObjectContext = [appDelegate managedObjectContext];
    NSFetchRequest* fetchRequest = [NSFetchRequest fetchRequestWithEntityName:@"Person"];
    NSError* nsError = nil;
    NSArray* res = [managedObjectContext executeFetchRequest:fetchRequest error:&nsError];
    if (res  == nil) {
        NSAssert(NO, @"Error executing fetch request: %@\n%@", [nsError localizedDescription], [nsError userInfo]);
    } else {
        [self.people addObjectsFromArray: res];
    };

    
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)addName:(id)sender {
    
    UIAlertController *alert = [UIAlertController alertControllerWithTitle:@"New Name" message:@"Add a new name" preferredStyle:UIAlertControllerStyleAlert];
    
    
    UIAlertAction *saveAction = [UIAlertAction actionWithTitle: @"Save"
        style:UIAlertActionStyleDefault
        handler:^(UIAlertAction *  action) {
            UITextField *textField = [alert.textFields firstObject];
            [self saveName:textField.text];
            [self.tableView reloadData];
            
    }];
    
    
    UIAlertAction *cancelAction = [UIAlertAction actionWithTitle:@"Cancel" style: UIAlertActionStyleDefault handler: ^(UIAlertAction*  action) {
        
    }];
    
    [alert addTextFieldWithConfigurationHandler:^(UITextField * textField) {
        
    }];
    
    [alert addAction:saveAction];
    [alert addAction:cancelAction];
    
    [self presentViewController:alert animated:true completion:nil];
}

-(void)saveName:(NSString *)name {
    
    AppDelegate* appDelegate =
        (AppDelegate*)[[UIApplication sharedApplication] delegate];
    NSManagedObjectContext* managedObjectContext = [appDelegate managedObjectContext];
    
    NSManagedObject* person = [NSEntityDescription insertNewObjectForEntityForName:@"Person" inManagedObjectContext:managedObjectContext];
                               
    [person setValue:name forKey:@"name"];
    
    NSError* nsError = nil;
    
    if ([managedObjectContext save:&nsError] ==NO) {
        NSAssert(NO, @"Error saving context: %@\n%@", [nsError localizedDescription], [nsError userInfo]);
    } else {
        [self.people addObject:person];
    };
    
}


-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    UITableViewCell *cell = [self.tableView dequeueReusableCellWithIdentifier: @"Cell"];
    cell.textLabel.text = [[self.people objectAtIndex:indexPath.row] valueForKey: @"name"];
    return cell;
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.people.count;
}

@end
