//
//  ViewController.h
//  HitList
//
//  Created by AlinaComp on 1/21/16.
//  Copyright © 2016 Razerware. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController <UITableViewDataSource>
@property (weak, nonatomic) IBOutlet UITableView *tableView;

- (IBAction)addName:(id)sender;


@end

