const functions = require("firebase-functions");
const admin = require("firebase-admin");
admin.initializeApp(functions.config().firebase);

// @ts-ignore
var database = admin.database();
var fcm = admin.messaging();

// exports.helloWorld = functions.https.onRequest((request, response) => {
//   response.send("Hello from Firebase!");
// });

// exports.textToLength = functions.https.onRequest((request, response) => {
//   var text = request.query.text;
//   var textLength = text.length;
//   response.send(String(textLength));
// });

const databaseParent = "devices";

exports.newNodeDetected = functions.database
  .ref(databaseParent + "/{deviceid}")
  .onWrite((change, context) => {
    var oldData = change.before.val();
    var newData = change.after.val();
    var deviceId = context.params.deviceid;
    var tokens = newData.usersInvolved;

    // database
    //   .ref("metadata/lastChangedName/")
    //   .set(userId + " changed his name from " + oldName + " to " + newName);
    // const tokens = querySnapshot.docs.map((snap) => snap.data().token);
    // var payload = {
    //   notification: {
    //     title: `Initial Reminder`,
    //     body: `Hello User`,
    //   },
    // };

    // // database
    // // .ref(databaseParent)
    // // .child(deviceId)
    // // .child("logs")
    // // .push({
    // //   title: `Fan is On`,
    // //   body: `Due to high temperature the ${deviceId} fan is on.`,
    // // });\

    if (oldData.temperature != newData.temperature) {
      if (newData.temperature > newData.minTemp) {
        var payload = {
          notification: {
            tag: "fan",
            title: `Fan is On (${newData.temperature} °C)`,
            body: `Due to high temperature the ${deviceId} fan is on.`,
          },
        };

        database
          .ref(databaseParent)
          .child(deviceId)
          .child("logs")
          .push({
            tag: "fan",
            title: `Fan is On (${newData.temperature} °C)`,
            body: `Due to high temperature the ${deviceId} fan is on.`,
            createdAt: Date.now(),
          });

        return fcm.sendToDevice(tokens, payload);
      } else {
        // @ts-ignore
        var payload = {
          notification: {
            tag: "fan",
            title: `Fan is Off (${newData.temperature} °C)`,
            body: `Temperature ${deviceId} is in normal state`,
          },
        };
        database
          .ref(databaseParent)
          .child(deviceId)
          .child("logs")
          .push({
            tag: "fan",
            title: `Fan is Off (${newData.temperature} °C)`,
            body: `Temperature ${deviceId} is in normal state`,
            createdAt: Date.now(),
          });
        return fcm.sendToDevice(tokens, payload);
      }
    } else if (oldData.currentSoilMoist1 != newData.currentSoilMoist1) {
      if (newData.currentSoilMoist1 > newData.minSoilMoist1) {
        // @ts-ignore
        var payload = {
          notification: {
            tag: "moisture1",
            title: `Water Pump No. 1 is On`,
            body: `The Moisture 1 detected that no water in your ${deviceId}`,
          },
        };
        database
          .ref(databaseParent)
          .child(deviceId)
          .child("logs")
          .push({
            tag: "moisture1",
            title: `Water Pump No. 1 is On`,
            body: `The Moisture 1 detected that no water in your ${deviceId}`,
            createdAt: Date.now(),
          });
        return fcm.sendToDevice(tokens, payload);
      } else {
        // @ts-ignore
        var payload = {
          notification: {
            tag: "moisture1",
            title: `Water Pump No. 1 is Off`,
            body: `The Moisture 1 detected that water is distributed in your ${deviceId}`,
          },
        };
        database
          .ref(databaseParent)
          .child(deviceId)
          .child("logs")
          .push({
            tag: "moisture1",
            title: `Water Pump No. 1 is Off`,
            body: `The Moisture 1 detected that water is distributed in your ${deviceId}`,
            createdAt: Date.now(),
          });
        return fcm.sendToDevice(tokens, payload);
      }
    } else if (oldData.currentSoilMoist2 != newData.currentSoilMoist2) {
      if (newData.currentSoilMoist2 > newData.minSoilMoist2) {
        // @ts-ignore
        var payload = {
          notification: {
            tag: "moisture2",
            title: `Water Pump No. 2 is On`,
            body: `The Moisture 2 detected that no water in your ${deviceId}`,
          },
        };
        database
          .ref(databaseParent)
          .child(deviceId)
          .child("logs")
          .push({
            tag: "moisture2",
            title: `Water Pump No. 2 is On`,
            body: `The Moisture 2 detected that no water in your ${deviceId}`,
            createdAt: Date.now(),
          });
        return fcm.sendToDevice(tokens, payload);
      } else {
        // @ts-ignore
        var payload = {
          notification: {
            tag: "moisture2",
            title: `Water Pump No. 2 is Off`,
            body: `The Moisture 2 detected that water is distributed in your ${deviceId}`,
          },
        };
        database
          .ref(databaseParent)
          .child(deviceId)
          .child("logs")
          .push({
            tag: "moisture2",
            title: `Water Pump No. 2 is Off`,
            body: `The Moisture 2 detected that water is distributed in your ${deviceId}`,
            createdAt: Date.now(),
          });
        return fcm.sendToDevice(tokens, payload);
      }
    } else if (oldData.currentSoilMoist3 != newData.currentSoilMoist3) {
      if (newData.currentSoilMoist3 > newData.minSoilMoist3) {
        // @ts-ignore
        var payload = {
          notification: {
            tag: "moisture3",
            title: `Water Pump No. 3 is On`,
            body: `The Moisture 3 detected that no water in your ${deviceId}`,
          },
        };
        database
          .ref(databaseParent)
          .child(deviceId)
          .child("logs")
          .push({
            tag: "moisture3",
            title: `Water Pump No. 3 is On`,
            body: `The Moisture 3 detected that no water in your ${deviceId}`,
            createdAt: Date.now(),
          });
        return fcm.sendToDevice(tokens, payload);
      } else {
        // @ts-ignore
        var payload = {
          notification: {
            tag: "moisture3",
            title: `Water Pump No. 3 is Off`,
            body: `The Moisture 3 detected that water is distributed in your ${deviceId}`,
          },
        };
        database
          .ref(databaseParent)
          .child(deviceId)
          .child("logs")
          .push({
            tag: "moisture3",
            title: `Water Pump No. 3 is Off`,
            body: `The Moisture 3 detected that water is distributed in your ${deviceId}`,
            createdAt: Date.now(),
          });
        return fcm.sendToDevice(tokens, payload);
      }
    } else if (oldData.currentSoilMoist4 != newData.currentSoilMoist4) {
      if (newData.currentSoilMoist4 > newData.minSoilMoist4) {
        // @ts-ignore
        var payload = {
          notification: {
            tag: "moisture4",
            title: `Water Pump No. 4 is On`,
            body: `The Moisture 4 detected that no water in your ${deviceId}`,
          },
        };
        database
          .ref(databaseParent)
          .child(deviceId)
          .child("logs")
          .push({
            tag: "moisture4",
            title: `Water Pump No. 4 is On`,
            body: `The Moisture 4 detected that no water in your ${deviceId}`,
            createdAt: Date.now(),
          });
        return fcm.sendToDevice(tokens, payload);
      } else {
        // @ts-ignore
        var payload = {
          notification: {
            tag: "moisture4",
            title: `Water Pump No. 4 is Off`,
            body: `The Moisture 4 detected that water is distributed in your ${deviceId}`,
          },
        };
        database
          .ref(databaseParent)
          .child(deviceId)
          .child("logs")
          .push({
            tag: "moisture4",
            title: `Water Pump No. 4 is Off`,
            body: `The Moisture 4 detected that water is distributed in your ${deviceId}`,
            createdAt: Date.now(),
          });
        return fcm.sendToDevice(tokens, payload);
      }
    }

    // return fcm.sendToDevice(tokens, payload);
  });

// exports.pushDataEveryMinute = functions.pubsub
//   .schedule("every 1 minutes")
//   .onRun((context) => {
//     var date = new Date();
//     database.ref("metadata/lastUpdate/").set(date.getTime());

//     return null;
//   });
