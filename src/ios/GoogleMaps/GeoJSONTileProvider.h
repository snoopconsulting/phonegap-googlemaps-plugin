//
//  GeoJSONTileProvider.h
//  app
//
//  Created by Ramiro Gonzalez Maciel on 6/8/15.
//
//

#import <GoogleMaps/GoogleMaps.h>
#import <Cordova/CDV.h>

@interface GeoJSONTileProvider : GMSSyncTileLayer //GMSTileLayer

@property (nonatomic, weak) UIWebView* webView;
@property (nonatomic, weak) id <CDVCommandDelegate> commandDelegate;

+ (instancetype)tileLayer;

@end
