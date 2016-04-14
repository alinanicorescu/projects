//
//  Foo.h
//  RandomApp
//
//  Created by Alina Nicorescu on 10/07/15.
//  Copyright (c) 2015 Alina Nicorescu. All rights reserved.
//

#import <Cocoa/Cocoa.h>

@interface Foo : NSObject {
    IBOutlet NSTextField *textField;
}
-(IBAction)seed:(id)sender;
-(IBAction)generate:(id)sender;

@end