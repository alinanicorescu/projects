//
//  AppDelegate.swift
//  test.orange
//
//  Created by Alina Nicorescu on 17/06/2020.
//  Copyright © 2020 Alina Nicorescu. All rights reserved.
//

import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {



    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        setUserDefaults()
        configUI()
        return true
    }

    private func setUserDefaults() {
        if UserDefaults.standard.object(forKey: Constants.USER_DEFAULT_RATE_KEY) == nil {
             UserDefaults.standard.set(Constants.DEFAULT_BASE_CURRENCY, forKey: Constants.USER_DEFAULT_RATE_KEY)
        }
        if UserDefaults.standard.object(forKey: Constants.USER_DEFAULT_REFRESH_TIME_KEY) == nil {
            UserDefaults.standard.set(Constants.DEFAULT_REFRESH_INTERVAL, forKey: Constants.USER_DEFAULT_REFRESH_TIME_KEY)
        }
        
    }
    
    private func configUI() {
        UITabBarItem.appearance().setTitleTextAttributes([NSAttributedString.Key.font : UIFont.boldSystemFont(ofSize: 16) ], for: .normal)
    }
    // MARK: UISceneSession Lifecycle

    func application(_ application: UIApplication, configurationForConnecting connectingSceneSession: UISceneSession, options: UIScene.ConnectionOptions) -> UISceneConfiguration {
        // Called when a new scene session is being created.
        // Use this method to select a configuration to create the new scene with.
        return UISceneConfiguration(name: "Default Configuration", sessionRole: connectingSceneSession.role)
    }

    func application(_ application: UIApplication, didDiscardSceneSessions sceneSessions: Set<UISceneSession>) {
        // Called when the user discards a scene session.
        // If any sessions were discarded while the application was not running, this will be called shortly after application:didFinishLaunchingWithOptions.
        // Use this method to release any resources that were specific to the discarded scenes, as they will not return.
    }


}

