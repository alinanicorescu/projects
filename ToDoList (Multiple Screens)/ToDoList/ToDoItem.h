//
//  ToDoItem.h
//  ToDoList
//
//  Created by Alina Nicorescu on 06/09/15.
//  Copyright (c) 2015 Alina Nicorescu. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ToDoItem : NSObject

@property NSString *itemName;
@property BOOL completed;
@property (readonly) NSDate *creationDate;

@end
