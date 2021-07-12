/*
 * Copyright (c) 2014, Codename One LTD. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mycompany.myapp.guiGhassen;

import com.codename1.capture.Capture;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.InteractionDialog;
import com.codename1.components.ToastBar;
import com.codename1.googlemaps.MapContainer;
import com.codename1.googlemaps.MapContainer.MapObject;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Util;
import com.codename1.maps.BoundingBox;
import com.codename1.maps.Coord;
import com.codename1.maps.MapListener;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import static com.codename1.ui.ComponentSelector.$;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.SideMenuBar;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Rectangle;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import com.codename1.util.MathUtil;
import javafx.scene.shape.SVGPath;

public class GoogleMapsTestApp {

    public static Image getScreenshot() {
        Form form = Display.getInstance().getCurrent();
        if (form != null) {
            Image screenshot = Image.createImage(form.getWidth(), form.getHeight());
            form.paintComponent(screenshot.getGraphics(), true);
            return screenshot;
        } else {
            return null;
        }
    }

    static String valMap = "LatLng(36.8252,10.1460)";

    public static String getValMap() {
        return valMap;
    }
    private static final String HTML_API_KEY = "AIzaSyBWeRU02YUYPdwRuMFyTKIXUbHjq6e35Gw";
    private Form current;

    public void init(Object context) {
        try {
            Resources theme = Resources.openLayered("/theme");
            UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
            Display.getInstance().setCommandBehavior(Display.COMMAND_BEHAVIOR_SIDE_NAVIGATION);
            UIManager.getInstance().getLookAndFeel().setMenuBarClass(SideMenuBar.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    MapObject sydney;

    public void start(Form previous) {
        if (current != null) {
            current.show();
            return;
        }
        Form hi = new Form("Map");
        hi.setLayout(new BorderLayout());
        MapContainer cnt = new MapContainer(HTML_API_KEY);
        //final MapContainer cnt = new MapContainer();
        cnt.setCameraPosition(new Coord(36.802086, 10.17679));//this breaks the code //because the Google map is not loaded yet
        cnt.addMapListener(new MapListener() {

            @Override
            public void mapPositionUpdated(Component source, int zoom, Coord center) {

                System.out.println("Map position updated: zoom=" + zoom + ", Center=" + center);
                double lati,longi;
                lati=MathUtil.round(center.getLatitude());
                longi=MathUtil.round(center.getLongitude());
                
                valMap = "LatLng(" + lati + "," +longi + ")";
                Image immg = getScreenshot();
                if (immg != null) {
                    try {
                        Date date = new Date();
                        long timeMilli = date.getTime();
                        String rogo = FileSystemStorage.getInstance().getAppHomePath() + "xyz"+timeMilli+".jpg";// + "kagha/Desktop/xyz.jpg"
                        System.out.println(FileSystemStorage.getInstance().getAppHomePath());
                        //System.out.println(rogo.substring(0, rogo.lastIndexOf(":")+1)+"/"+rogo.substring(rogo.lastIndexOf(":")+1));
                        OutputStream os = FileSystemStorage.getInstance().openOutputStream(rogo);

                        ImageIO.getImageIO().save(immg, os, ImageIO.FORMAT_JPEG, 0.9f);
                        os.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        cnt.addLongPressListener(e -> {
            System.out.println("Long press");
            ToastBar.showMessage("Received longPress at " + e.getX() + ", " + e.getY(), FontImage.MATERIAL_3D_ROTATION);
        });
        cnt.addTapListener(e -> {
            ToastBar.showMessage("Received tap at " + e.getX() + ", " + e.getY(), FontImage.MATERIAL_3D_ROTATION);
        });

        int maxZoom = cnt.getMaxZoom();
        System.out.println("Max zoom is " + maxZoom);
        Button btnMoveCamera = new Button("Move Camera");
        btnMoveCamera.addActionListener(e -> {
            cnt.setCameraPosition(new Coord(-33.867, 151.206));
        });
        Style s = new Style();
        s.setFgColor(0xff0000);
        s.setBgTransparency(0);
        FontImage markerImg = FontImage.createMaterial(FontImage.MATERIAL_PLACE, s, 3);

        Button btnAddMarker = new Button("Add Marker");
        btnAddMarker.addActionListener(e -> {

            cnt.setCameraPosition(new Coord(41.889, -87.622));
            cnt.addMarker(EncodedImage.createFromImage(markerImg, false), cnt.getCameraPosition(), "Hi marker", "Optional long description", new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    System.out.println("Bounding box is " + cnt.getBoundingBox());
                    ToastBar.showMessage("You clicked the marker", FontImage.MATERIAL_PLACE);
                }
            });

        });

        Button testCoordPositions = $(new Button("Test Coords"))
                .addActionListener(e -> {
                    Coord topLeft = cnt.getCoordAtPosition(0, 0);
                    System.out.println("Top Left is " + topLeft + " -> " + cnt.getScreenCoordinate(topLeft) + " Should be (0,0)");
                    Coord bottomRight = cnt.getCoordAtPosition(cnt.getWidth(), cnt.getHeight());
                    System.out.println("Bottom right is " + bottomRight + " -> " + cnt.getScreenCoordinate(bottomRight) + " Should be " + cnt.getWidth() + ", " + cnt.getHeight());
                    Coord bottomLeft = cnt.getCoordAtPosition(0, cnt.getHeight());
                    System.out.println("Bottom Left is " + bottomLeft + " -> " + cnt.getScreenCoordinate(bottomLeft) + " Should be 0, " + cnt.getHeight());
                    Coord topRight = cnt.getCoordAtPosition(cnt.getWidth(), 0);
                    System.out.println("Top right is " + topRight + " -> " + cnt.getScreenCoordinate(topRight) + " Should be " + cnt.getWidth() + ", 0");
                    Coord center = cnt.getCoordAtPosition(cnt.getWidth() / 2, cnt.getHeight() / 2);
                    System.out.println("Center is " + center + " -> " + cnt.getScreenCoordinate(center) + ", should be " + (cnt.getWidth() / 2) + ", " + (cnt.getHeight() / 2));
                    EncodedImage encImg = EncodedImage.createFromImage(markerImg, false);
                    cnt.addMarker(encImg, topLeft, "Top Left", "Top Left", null);
                    cnt.addMarker(encImg, topRight, "Top Right", "Top Right", null);
                    cnt.addMarker(encImg, bottomRight, "Bottom Right", "Bottom Right", null);
                    cnt.addMarker(encImg, bottomLeft, "Bottom Left", "Bottom Left", null);
                    cnt.addMarker(encImg, center, "Center", "Center", null);

                })
                .asComponent(Button.class);

        Button toggleTopMargin = $(new Button("Toggle Margin"))
                .addActionListener(e -> {
                    int marginTop = $(cnt).getStyle().getMarginTop();
                    if (marginTop < Display.getInstance().getDisplayHeight() / 3) {
                        $(cnt).selectAllStyles().setMargin(Display.getInstance().getDisplayHeight() / 3, 0, 0, 0);
                    } else {
                        $(cnt).selectAllStyles().setMargin(0, 0, 0, 0);
                    }
                    $(cnt).getComponentForm().revalidate();
                })
                .asComponent(Button.class);

        Button btnClearAll = new Button("Clear All");
        btnClearAll.addActionListener(e -> {
            cnt.clearMapLayers();
        });

        MapObject mo = cnt.addMarker(EncodedImage.createFromImage(markerImg, false), new Coord(-33.866, 151.195), "test", "test", e -> {
            System.out.println("Marker clicked");
            cnt.removeMapObject(sydney);
        });
        sydney = mo;
        System.out.println("MO is " + mo);
        mo = cnt.addMarker(EncodedImage.createFromImage(markerImg, false), new Coord(-18.142, 178.431), "test", "test", e -> {
            System.out.println("Marker clicked");
        });
        System.out.println("MO is " + mo);
        cnt.addTapListener(e -> {
            if (tapDisabled) {
                return;
            }
            tapDisabled = true;
            TextField enterName = new TextField();
            Container wrapper = BoxLayout.encloseY(new Label("Name:"), enterName);
            InteractionDialog dlg = new InteractionDialog("Add Marker");
            dlg.getContentPane().add(wrapper);
            enterName.setDoneListener(e2 -> {
                String txt = enterName.getText();
                cnt.addMarker(EncodedImage.createFromImage(markerImg, false), cnt.getCoordAtPosition(e.getX(), e.getY()), enterName.getText(), "", e3 -> {
                    ToastBar.showMessage("You clicked " + txt, FontImage.MATERIAL_PLACE);
                });
                dlg.dispose();
                tapDisabled = false;
            });
            dlg.showPopupDialog(new Rectangle(e.getX(), e.getY(), 10, 10));
            enterName.startEditingAsync();
        });

        FloatingActionButton nextForm = FloatingActionButton.createFAB(FontImage.MATERIAL_ACCESS_ALARM);

        Container root = LayeredLayout.encloseIn(cnt);
        hi.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,
                e -> previous.showBack()); // Revenir vers l'interface précédente
        hi.add(BorderLayout.CENTER, root);
        hi.show();

    }
    boolean tapDisabled = false;

    public void stop() {
        current = Display.getInstance().getCurrent();
    }

    public void destroy() {
    }

}
