/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// identity function for calling harmony imports with the correct context
/******/ 	__webpack_require__.i = function(value) { return value; };
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, {
/******/ 				configurable: false,
/******/ 				enumerable: true,
/******/ 				get: getter
/******/ 			});
/******/ 		}
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 47);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ (function(module, exports) {

var g;

// This works in non-strict mode
g = (function() {
	return this;
})();

try {
	// This works if eval is allowed (see CSP)
	g = g || Function("return this")() || (1,eval)("this");
} catch(e) {
	// This works if the window reference is available
	if(typeof window === "object")
		g = window;
}

// g can still be undefined, but nothing to do about it...
// We return undefined, instead of nothing here, so it's
// easier to handle this case. if(!global) { ...}

module.exports = g;


/***/ }),
/* 1 */
/***/ (function(module, exports, __webpack_require__) {


/**
 * Expose `Emitter`.
 */

if (true) {
  module.exports = Emitter;
}

/**
 * Initialize a new `Emitter`.
 *
 * @api public
 */

function Emitter(obj) {
  if (obj) return mixin(obj);
};

/**
 * Mixin the emitter properties.
 *
 * @param {Object} obj
 * @return {Object}
 * @api private
 */

function mixin(obj) {
  for (var key in Emitter.prototype) {
    obj[key] = Emitter.prototype[key];
  }
  return obj;
}

/**
 * Listen on the given `event` with `fn`.
 *
 * @param {String} event
 * @param {Function} fn
 * @return {Emitter}
 * @api public
 */

Emitter.prototype.on =
Emitter.prototype.addEventListener = function(event, fn){
  this._callbacks = this._callbacks || {};
  (this._callbacks['$' + event] = this._callbacks['$' + event] || [])
    .push(fn);
  return this;
};

/**
 * Adds an `event` listener that will be invoked a single
 * time then automatically removed.
 *
 * @param {String} event
 * @param {Function} fn
 * @return {Emitter}
 * @api public
 */

Emitter.prototype.once = function(event, fn){
  function on() {
    this.off(event, on);
    fn.apply(this, arguments);
  }

  on.fn = fn;
  this.on(event, on);
  return this;
};

/**
 * Remove the given callback for `event` or all
 * registered callbacks.
 *
 * @param {String} event
 * @param {Function} fn
 * @return {Emitter}
 * @api public
 */

Emitter.prototype.off =
Emitter.prototype.removeListener =
Emitter.prototype.removeAllListeners =
Emitter.prototype.removeEventListener = function(event, fn){
  this._callbacks = this._callbacks || {};

  // all
  if (0 == arguments.length) {
    this._callbacks = {};
    return this;
  }

  // specific event
  var callbacks = this._callbacks['$' + event];
  if (!callbacks) return this;

  // remove all handlers
  if (1 == arguments.length) {
    delete this._callbacks['$' + event];
    return this;
  }

  // remove specific handler
  var cb;
  for (var i = 0; i < callbacks.length; i++) {
    cb = callbacks[i];
    if (cb === fn || cb.fn === fn) {
      callbacks.splice(i, 1);
      break;
    }
  }
  return this;
};

/**
 * Emit `event` with the given args.
 *
 * @param {String} event
 * @param {Mixed} ...
 * @return {Emitter}
 */

Emitter.prototype.emit = function(event){
  this._callbacks = this._callbacks || {};
  var args = [].slice.call(arguments, 1)
    , callbacks = this._callbacks['$' + event];

  if (callbacks) {
    callbacks = callbacks.slice(0);
    for (var i = 0, len = callbacks.length; i < len; ++i) {
      callbacks[i].apply(this, args);
    }
  }

  return this;
};

/**
 * Return array of callbacks for `event`.
 *
 * @param {String} event
 * @return {Array}
 * @api public
 */

Emitter.prototype.listeners = function(event){
  this._callbacks = this._callbacks || {};
  return this._callbacks['$' + event] || [];
};

/**
 * Check if this emitter has `event` handlers.
 *
 * @param {String} event
 * @return {Boolean}
 * @api public
 */

Emitter.prototype.hasListeners = function(event){
  return !! this.listeners(event).length;
};


/***/ }),
/* 2 */
/***/ (function(module, exports, __webpack_require__) {

/* WEBPACK VAR INJECTION */(function(global) {/**
 * Module dependencies.
 */

var keys = __webpack_require__(59);
var hasBinary = __webpack_require__(19);
var sliceBuffer = __webpack_require__(44);
var after = __webpack_require__(43);
var utf8 = __webpack_require__(60);

var base64encoder;
if (global && global.ArrayBuffer) {
  base64encoder = __webpack_require__(49);
}

/**
 * Check if we are running an android browser. That requires us to use
 * ArrayBuffer with polling transports...
 *
 * http://ghinda.net/jpeg-blob-ajax-android/
 */

var isAndroid = typeof navigator !== 'undefined' && /Android/i.test(navigator.userAgent);

/**
 * Check if we are running in PhantomJS.
 * Uploading a Blob with PhantomJS does not work correctly, as reported here:
 * https://github.com/ariya/phantomjs/issues/11395
 * @type boolean
 */
var isPhantomJS = typeof navigator !== 'undefined' && /PhantomJS/i.test(navigator.userAgent);

/**
 * When true, avoids using Blobs to encode payloads.
 * @type boolean
 */
var dontSendBlobs = isAndroid || isPhantomJS;

/**
 * Current protocol version.
 */

exports.protocol = 3;

/**
 * Packet types.
 */

var packets = exports.packets = {
    open:     0    // non-ws
  , close:    1    // non-ws
  , ping:     2
  , pong:     3
  , message:  4
  , upgrade:  5
  , noop:     6
};

var packetslist = keys(packets);

/**
 * Premade error packet.
 */

var err = { type: 'error', data: 'parser error' };

/**
 * Create a blob api even for blob builder when vendor prefixes exist
 */

var Blob = __webpack_require__(50);

/**
 * Encodes a packet.
 *
 *     <packet type id> [ <data> ]
 *
 * Example:
 *
 *     5hello world
 *     3
 *     4
 *
 * Binary is encoded in an identical principle
 *
 * @api private
 */

exports.encodePacket = function (packet, supportsBinary, utf8encode, callback) {
  if (typeof supportsBinary === 'function') {
    callback = supportsBinary;
    supportsBinary = false;
  }

  if (typeof utf8encode === 'function') {
    callback = utf8encode;
    utf8encode = null;
  }

  var data = (packet.data === undefined)
    ? undefined
    : packet.data.buffer || packet.data;

  if (global.ArrayBuffer && data instanceof ArrayBuffer) {
    return encodeArrayBuffer(packet, supportsBinary, callback);
  } else if (Blob && data instanceof global.Blob) {
    return encodeBlob(packet, supportsBinary, callback);
  }

  // might be an object with { base64: true, data: dataAsBase64String }
  if (data && data.base64) {
    return encodeBase64Object(packet, callback);
  }

  // Sending data as a utf-8 string
  var encoded = packets[packet.type];

  // data fragment is optional
  if (undefined !== packet.data) {
    encoded += utf8encode ? utf8.encode(String(packet.data), { strict: false }) : String(packet.data);
  }

  return callback('' + encoded);

};

function encodeBase64Object(packet, callback) {
  // packet data is an object { base64: true, data: dataAsBase64String }
  var message = 'b' + exports.packets[packet.type] + packet.data.data;
  return callback(message);
}

/**
 * Encode packet helpers for binary types
 */

function encodeArrayBuffer(packet, supportsBinary, callback) {
  if (!supportsBinary) {
    return exports.encodeBase64Packet(packet, callback);
  }

  var data = packet.data;
  var contentArray = new Uint8Array(data);
  var resultBuffer = new Uint8Array(1 + data.byteLength);

  resultBuffer[0] = packets[packet.type];
  for (var i = 0; i < contentArray.length; i++) {
    resultBuffer[i+1] = contentArray[i];
  }

  return callback(resultBuffer.buffer);
}

function encodeBlobAsArrayBuffer(packet, supportsBinary, callback) {
  if (!supportsBinary) {
    return exports.encodeBase64Packet(packet, callback);
  }

  var fr = new FileReader();
  fr.onload = function() {
    packet.data = fr.result;
    exports.encodePacket(packet, supportsBinary, true, callback);
  };
  return fr.readAsArrayBuffer(packet.data);
}

function encodeBlob(packet, supportsBinary, callback) {
  if (!supportsBinary) {
    return exports.encodeBase64Packet(packet, callback);
  }

  if (dontSendBlobs) {
    return encodeBlobAsArrayBuffer(packet, supportsBinary, callback);
  }

  var length = new Uint8Array(1);
  length[0] = packets[packet.type];
  var blob = new Blob([length.buffer, packet.data]);

  return callback(blob);
}

/**
 * Encodes a packet with binary data in a base64 string
 *
 * @param {Object} packet, has `type` and `data`
 * @return {String} base64 encoded message
 */

exports.encodeBase64Packet = function(packet, callback) {
  var message = 'b' + exports.packets[packet.type];
  if (Blob && packet.data instanceof global.Blob) {
    var fr = new FileReader();
    fr.onload = function() {
      var b64 = fr.result.split(',')[1];
      callback(message + b64);
    };
    return fr.readAsDataURL(packet.data);
  }

  var b64data;
  try {
    b64data = String.fromCharCode.apply(null, new Uint8Array(packet.data));
  } catch (e) {
    // iPhone Safari doesn't let you apply with typed arrays
    var typed = new Uint8Array(packet.data);
    var basic = new Array(typed.length);
    for (var i = 0; i < typed.length; i++) {
      basic[i] = typed[i];
    }
    b64data = String.fromCharCode.apply(null, basic);
  }
  message += global.btoa(b64data);
  return callback(message);
};

/**
 * Decodes a packet. Changes format to Blob if requested.
 *
 * @return {Object} with `type` and `data` (if any)
 * @api private
 */

exports.decodePacket = function (data, binaryType, utf8decode) {
  if (data === undefined) {
    return err;
  }
  // String data
  if (typeof data === 'string') {
    if (data.charAt(0) === 'b') {
      return exports.decodeBase64Packet(data.substr(1), binaryType);
    }

    if (utf8decode) {
      data = tryDecode(data);
      if (data === false) {
        return err;
      }
    }
    var type = data.charAt(0);

    if (Number(type) != type || !packetslist[type]) {
      return err;
    }

    if (data.length > 1) {
      return { type: packetslist[type], data: data.substring(1) };
    } else {
      return { type: packetslist[type] };
    }
  }

  var asArray = new Uint8Array(data);
  var type = asArray[0];
  var rest = sliceBuffer(data, 1);
  if (Blob && binaryType === 'blob') {
    rest = new Blob([rest]);
  }
  return { type: packetslist[type], data: rest };
};

function tryDecode(data) {
  try {
    data = utf8.decode(data, { strict: false });
  } catch (e) {
    return false;
  }
  return data;
}

/**
 * Decodes a packet encoded in a base64 string
 *
 * @param {String} base64 encoded message
 * @return {Object} with `type` and `data` (if any)
 */

exports.decodeBase64Packet = function(msg, binaryType) {
  var type = packetslist[msg.charAt(0)];
  if (!base64encoder) {
    return { type: type, data: { base64: true, data: msg.substr(1) } };
  }

  var data = base64encoder.decode(msg.substr(1));

  if (binaryType === 'blob' && Blob) {
    data = new Blob([data]);
  }

  return { type: type, data: data };
};

/**
 * Encodes multiple messages (payload).
 *
 *     <length>:data
 *
 * Example:
 *
 *     11:hello world2:hi
 *
 * If any contents are binary, they will be encoded as base64 strings. Base64
 * encoded strings are marked with a b before the length specifier
 *
 * @param {Array} packets
 * @api private
 */

exports.encodePayload = function (packets, supportsBinary, callback) {
  if (typeof supportsBinary === 'function') {
    callback = supportsBinary;
    supportsBinary = null;
  }

  var isBinary = hasBinary(packets);

  if (supportsBinary && isBinary) {
    if (Blob && !dontSendBlobs) {
      return exports.encodePayloadAsBlob(packets, callback);
    }

    return exports.encodePayloadAsArrayBuffer(packets, callback);
  }

  if (!packets.length) {
    return callback('0:');
  }

  function setLengthHeader(message) {
    return message.length + ':' + message;
  }

  function encodeOne(packet, doneCallback) {
    exports.encodePacket(packet, !isBinary ? false : supportsBinary, false, function(message) {
      doneCallback(null, setLengthHeader(message));
    });
  }

  map(packets, encodeOne, function(err, results) {
    return callback(results.join(''));
  });
};

/**
 * Async array map using after
 */

function map(ary, each, done) {
  var result = new Array(ary.length);
  var next = after(ary.length, done);

  var eachWithIndex = function(i, el, cb) {
    each(el, function(error, msg) {
      result[i] = msg;
      cb(error, result);
    });
  };

  for (var i = 0; i < ary.length; i++) {
    eachWithIndex(i, ary[i], next);
  }
}

/*
 * Decodes data when a payload is maybe expected. Possible binary contents are
 * decoded from their base64 representation
 *
 * @param {String} data, callback method
 * @api public
 */

exports.decodePayload = function (data, binaryType, callback) {
  if (typeof data !== 'string') {
    return exports.decodePayloadAsBinary(data, binaryType, callback);
  }

  if (typeof binaryType === 'function') {
    callback = binaryType;
    binaryType = null;
  }

  var packet;
  if (data === '') {
    // parser error - ignoring payload
    return callback(err, 0, 1);
  }

  var length = '', n, msg;

  for (var i = 0, l = data.length; i < l; i++) {
    var chr = data.charAt(i);

    if (chr !== ':') {
      length += chr;
      continue;
    }

    if (length === '' || (length != (n = Number(length)))) {
      // parser error - ignoring payload
      return callback(err, 0, 1);
    }

    msg = data.substr(i + 1, n);

    if (length != msg.length) {
      // parser error - ignoring payload
      return callback(err, 0, 1);
    }

    if (msg.length) {
      packet = exports.decodePacket(msg, binaryType, false);

      if (err.type === packet.type && err.data === packet.data) {
        // parser error in individual packet - ignoring payload
        return callback(err, 0, 1);
      }

      var ret = callback(packet, i + n, l);
      if (false === ret) return;
    }

    // advance cursor
    i += n;
    length = '';
  }

  if (length !== '') {
    // parser error - ignoring payload
    return callback(err, 0, 1);
  }

};

/**
 * Encodes multiple messages (payload) as binary.
 *
 * <1 = binary, 0 = string><number from 0-9><number from 0-9>[...]<number
 * 255><data>
 *
 * Example:
 * 1 3 255 1 2 3, if the binary contents are interpreted as 8 bit integers
 *
 * @param {Array} packets
 * @return {ArrayBuffer} encoded payload
 * @api private
 */

exports.encodePayloadAsArrayBuffer = function(packets, callback) {
  if (!packets.length) {
    return callback(new ArrayBuffer(0));
  }

  function encodeOne(packet, doneCallback) {
    exports.encodePacket(packet, true, true, function(data) {
      return doneCallback(null, data);
    });
  }

  map(packets, encodeOne, function(err, encodedPackets) {
    var totalLength = encodedPackets.reduce(function(acc, p) {
      var len;
      if (typeof p === 'string'){
        len = p.length;
      } else {
        len = p.byteLength;
      }
      return acc + len.toString().length + len + 2; // string/binary identifier + separator = 2
    }, 0);

    var resultArray = new Uint8Array(totalLength);

    var bufferIndex = 0;
    encodedPackets.forEach(function(p) {
      var isString = typeof p === 'string';
      var ab = p;
      if (isString) {
        var view = new Uint8Array(p.length);
        for (var i = 0; i < p.length; i++) {
          view[i] = p.charCodeAt(i);
        }
        ab = view.buffer;
      }

      if (isString) { // not true binary
        resultArray[bufferIndex++] = 0;
      } else { // true binary
        resultArray[bufferIndex++] = 1;
      }

      var lenStr = ab.byteLength.toString();
      for (var i = 0; i < lenStr.length; i++) {
        resultArray[bufferIndex++] = parseInt(lenStr[i]);
      }
      resultArray[bufferIndex++] = 255;

      var view = new Uint8Array(ab);
      for (var i = 0; i < view.length; i++) {
        resultArray[bufferIndex++] = view[i];
      }
    });

    return callback(resultArray.buffer);
  });
};

/**
 * Encode as Blob
 */

exports.encodePayloadAsBlob = function(packets, callback) {
  function encodeOne(packet, doneCallback) {
    exports.encodePacket(packet, true, true, function(encoded) {
      var binaryIdentifier = new Uint8Array(1);
      binaryIdentifier[0] = 1;
      if (typeof encoded === 'string') {
        var view = new Uint8Array(encoded.length);
        for (var i = 0; i < encoded.length; i++) {
          view[i] = encoded.charCodeAt(i);
        }
        encoded = view.buffer;
        binaryIdentifier[0] = 0;
      }

      var len = (encoded instanceof ArrayBuffer)
        ? encoded.byteLength
        : encoded.size;

      var lenStr = len.toString();
      var lengthAry = new Uint8Array(lenStr.length + 1);
      for (var i = 0; i < lenStr.length; i++) {
        lengthAry[i] = parseInt(lenStr[i]);
      }
      lengthAry[lenStr.length] = 255;

      if (Blob) {
        var blob = new Blob([binaryIdentifier.buffer, lengthAry.buffer, encoded]);
        doneCallback(null, blob);
      }
    });
  }

  map(packets, encodeOne, function(err, results) {
    return callback(new Blob(results));
  });
};

/*
 * Decodes data when a payload is maybe expected. Strings are decoded by
 * interpreting each byte as a key code for entries marked to start with 0. See
 * description of encodePayloadAsBinary
 *
 * @param {ArrayBuffer} data, callback method
 * @api public
 */

exports.decodePayloadAsBinary = function (data, binaryType, callback) {
  if (typeof binaryType === 'function') {
    callback = binaryType;
    binaryType = null;
  }

  var bufferTail = data;
  var buffers = [];

  while (bufferTail.byteLength > 0) {
    var tailArray = new Uint8Array(bufferTail);
    var isString = tailArray[0] === 0;
    var msgLength = '';

    for (var i = 1; ; i++) {
      if (tailArray[i] === 255) break;

      // 310 = char length of Number.MAX_VALUE
      if (msgLength.length > 310) {
        return callback(err, 0, 1);
      }

      msgLength += tailArray[i];
    }

    bufferTail = sliceBuffer(bufferTail, 2 + msgLength.length);
    msgLength = parseInt(msgLength);

    var msg = sliceBuffer(bufferTail, 0, msgLength);
    if (isString) {
      try {
        msg = String.fromCharCode.apply(null, new Uint8Array(msg));
      } catch (e) {
        // iPhone Safari doesn't let you apply to typed arrays
        var typed = new Uint8Array(msg);
        msg = '';
        for (var i = 0; i < typed.length; i++) {
          msg += String.fromCharCode(typed[i]);
        }
      }
    }

    buffers.push(msg);
    bufferTail = sliceBuffer(bufferTail, msgLength);
  }

  var total = buffers.length;
  buffers.forEach(function(buffer, i) {
    callback(exports.decodePacket(buffer, binaryType, true), i, total);
  });
};

/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(0)))

/***/ }),
/* 3 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var IDrawing = function () {
    function IDrawing(render, name, option) {
        _classCallCheck(this, IDrawing);

        if (render) {
            this.entitySet = render.entitySet;
            this.render = render;
            this.render.addDrawing(this);
        }
    }

    _createClass(IDrawing, [{
        key: "preDraw",
        value: function preDraw(canvasCxt, render) {
            // this.render.clearRender()
            canvasCxt.save();
        }
    }, {
        key: "onDraw",
        value: function onDraw(canvasCxt, render) {}
    }, {
        key: "drawEnd",
        value: function drawEnd(canvasCxt, render) {
            canvasCxt.restore();
        }
    }, {
        key: "getOrder",
        value: function getOrder() {
            return 0;
        }
    }]);

    return IDrawing;
}();

exports.default = IDrawing;

/***/ }),
/* 4 */
/***/ (function(module, exports) {


module.exports = function(a, b){
  var fn = function(){};
  fn.prototype = b.prototype;
  a.prototype = new fn;
  a.prototype.constructor = a;
};

/***/ }),
/* 5 */
/***/ (function(module, exports, __webpack_require__) {

/* WEBPACK VAR INJECTION */(function(process) {/**
 * This is the web browser implementation of `debug()`.
 *
 * Expose `debug()` as the module.
 */

exports = module.exports = __webpack_require__(57);
exports.log = log;
exports.formatArgs = formatArgs;
exports.save = save;
exports.load = load;
exports.useColors = useColors;
exports.storage = 'undefined' != typeof chrome
               && 'undefined' != typeof chrome.storage
                  ? chrome.storage.local
                  : localstorage();

/**
 * Colors.
 */

exports.colors = [
  'lightseagreen',
  'forestgreen',
  'goldenrod',
  'dodgerblue',
  'darkorchid',
  'crimson'
];

/**
 * Currently only WebKit-based Web Inspectors, Firefox >= v31,
 * and the Firebug extension (any Firefox version) are known
 * to support "%c" CSS customizations.
 *
 * TODO: add a `localStorage` variable to explicitly enable/disable colors
 */

function useColors() {
  // NB: In an Electron preload script, document will be defined but not fully
  // initialized. Since we know we're in Chrome, we'll just detect this case
  // explicitly
  if (typeof window !== 'undefined' && window.process && window.process.type === 'renderer') {
    return true;
  }

  // is webkit? http://stackoverflow.com/a/16459606/376773
  // document is undefined in react-native: https://github.com/facebook/react-native/pull/1632
  return (typeof document !== 'undefined' && document.documentElement && document.documentElement.style && document.documentElement.style.WebkitAppearance) ||
    // is firebug? http://stackoverflow.com/a/398120/376773
    (typeof window !== 'undefined' && window.console && (window.console.firebug || (window.console.exception && window.console.table))) ||
    // is firefox >= v31?
    // https://developer.mozilla.org/en-US/docs/Tools/Web_Console#Styling_messages
    (typeof navigator !== 'undefined' && navigator.userAgent && navigator.userAgent.toLowerCase().match(/firefox\/(\d+)/) && parseInt(RegExp.$1, 10) >= 31) ||
    // double check webkit in userAgent just in case we are in a worker
    (typeof navigator !== 'undefined' && navigator.userAgent && navigator.userAgent.toLowerCase().match(/applewebkit\/(\d+)/));
}

/**
 * Map %j to `JSON.stringify()`, since no Web Inspectors do that by default.
 */

exports.formatters.j = function(v) {
  try {
    return JSON.stringify(v);
  } catch (err) {
    return '[UnexpectedJSONParseError]: ' + err.message;
  }
};


/**
 * Colorize log arguments if enabled.
 *
 * @api public
 */

function formatArgs(args) {
  var useColors = this.useColors;

  args[0] = (useColors ? '%c' : '')
    + this.namespace
    + (useColors ? ' %c' : ' ')
    + args[0]
    + (useColors ? '%c ' : ' ')
    + '+' + exports.humanize(this.diff);

  if (!useColors) return;

  var c = 'color: ' + this.color;
  args.splice(1, 0, c, 'color: inherit')

  // the final "%c" is somewhat tricky, because there could be other
  // arguments passed either before or after the %c, so we need to
  // figure out the correct index to insert the CSS into
  var index = 0;
  var lastC = 0;
  args[0].replace(/%[a-zA-Z%]/g, function(match) {
    if ('%%' === match) return;
    index++;
    if ('%c' === match) {
      // we only are interested in the *last* %c
      // (the user may have provided their own)
      lastC = index;
    }
  });

  args.splice(lastC, 0, c);
}

/**
 * Invokes `console.log()` when available.
 * No-op when `console.log` is not a "function".
 *
 * @api public
 */

function log() {
  // this hackery is required for IE8/9, where
  // the `console.log` function doesn't have 'apply'
  return 'object' === typeof console
    && console.log
    && Function.prototype.apply.call(console.log, console, arguments);
}

/**
 * Save `namespaces`.
 *
 * @param {String} namespaces
 * @api private
 */

function save(namespaces) {
  try {
    if (null == namespaces) {
      exports.storage.removeItem('debug');
    } else {
      exports.storage.debug = namespaces;
    }
  } catch(e) {}
}

/**
 * Load `namespaces`.
 *
 * @return {String} returns the previously persisted debug modes
 * @api private
 */

function load() {
  var r;
  try {
    r = exports.storage.debug;
  } catch(e) {}

  // If debug isn't set in LS, and we're in Electron, try to load $DEBUG
  if (!r && typeof process !== 'undefined' && 'env' in process) {
    r = process.env.DEBUG;
  }

  return r;
}

/**
 * Enable namespaces listed in `localStorage.debug` initially.
 */

exports.enable(load());

/**
 * Localstorage attempts to return the localstorage.
 *
 * This is necessary because safari throws
 * when a user disables cookies/localstorage
 * and you attempt to access it.
 *
 * @return {LocalStorage}
 * @api private
 */

function localstorage() {
  try {
    return window.localStorage;
  } catch (e) {}
}

/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(8)))

/***/ }),
/* 6 */
/***/ (function(module, exports) {

/**
 * Compiles a querystring
 * Returns string representation of the object
 *
 * @param {Object}
 * @api private
 */

exports.encode = function (obj) {
  var str = '';

  for (var i in obj) {
    if (obj.hasOwnProperty(i)) {
      if (str.length) str += '&';
      str += encodeURIComponent(i) + '=' + encodeURIComponent(obj[i]);
    }
  }

  return str;
};

/**
 * Parses a simple querystring into an object
 *
 * @param {String} qs
 * @api private
 */

exports.decode = function(qs){
  var qry = {};
  var pairs = qs.split('&');
  for (var i = 0, l = pairs.length; i < l; i++) {
    var pair = pairs[i].split('=');
    qry[decodeURIComponent(pair[0])] = decodeURIComponent(pair[1]);
  }
  return qry;
};


/***/ }),
/* 7 */
/***/ (function(module, exports, __webpack_require__) {

/* WEBPACK VAR INJECTION */(function(process) {/**
 * This is the web browser implementation of `debug()`.
 *
 * Expose `debug()` as the module.
 */

exports = module.exports = __webpack_require__(64);
exports.log = log;
exports.formatArgs = formatArgs;
exports.save = save;
exports.load = load;
exports.useColors = useColors;
exports.storage = 'undefined' != typeof chrome
               && 'undefined' != typeof chrome.storage
                  ? chrome.storage.local
                  : localstorage();

/**
 * Colors.
 */

exports.colors = [
  'lightseagreen',
  'forestgreen',
  'goldenrod',
  'dodgerblue',
  'darkorchid',
  'crimson'
];

/**
 * Currently only WebKit-based Web Inspectors, Firefox >= v31,
 * and the Firebug extension (any Firefox version) are known
 * to support "%c" CSS customizations.
 *
 * TODO: add a `localStorage` variable to explicitly enable/disable colors
 */

function useColors() {
  // NB: In an Electron preload script, document will be defined but not fully
  // initialized. Since we know we're in Chrome, we'll just detect this case
  // explicitly
  if (typeof window !== 'undefined' && window.process && window.process.type === 'renderer') {
    return true;
  }

  // is webkit? http://stackoverflow.com/a/16459606/376773
  // document is undefined in react-native: https://github.com/facebook/react-native/pull/1632
  return (typeof document !== 'undefined' && document.documentElement && document.documentElement.style && document.documentElement.style.WebkitAppearance) ||
    // is firebug? http://stackoverflow.com/a/398120/376773
    (typeof window !== 'undefined' && window.console && (window.console.firebug || (window.console.exception && window.console.table))) ||
    // is firefox >= v31?
    // https://developer.mozilla.org/en-US/docs/Tools/Web_Console#Styling_messages
    (typeof navigator !== 'undefined' && navigator.userAgent && navigator.userAgent.toLowerCase().match(/firefox\/(\d+)/) && parseInt(RegExp.$1, 10) >= 31) ||
    // double check webkit in userAgent just in case we are in a worker
    (typeof navigator !== 'undefined' && navigator.userAgent && navigator.userAgent.toLowerCase().match(/applewebkit\/(\d+)/));
}

/**
 * Map %j to `JSON.stringify()`, since no Web Inspectors do that by default.
 */

exports.formatters.j = function(v) {
  try {
    return JSON.stringify(v);
  } catch (err) {
    return '[UnexpectedJSONParseError]: ' + err.message;
  }
};


/**
 * Colorize log arguments if enabled.
 *
 * @api public
 */

function formatArgs(args) {
  var useColors = this.useColors;

  args[0] = (useColors ? '%c' : '')
    + this.namespace
    + (useColors ? ' %c' : ' ')
    + args[0]
    + (useColors ? '%c ' : ' ')
    + '+' + exports.humanize(this.diff);

  if (!useColors) return;

  var c = 'color: ' + this.color;
  args.splice(1, 0, c, 'color: inherit')

  // the final "%c" is somewhat tricky, because there could be other
  // arguments passed either before or after the %c, so we need to
  // figure out the correct index to insert the CSS into
  var index = 0;
  var lastC = 0;
  args[0].replace(/%[a-zA-Z%]/g, function(match) {
    if ('%%' === match) return;
    index++;
    if ('%c' === match) {
      // we only are interested in the *last* %c
      // (the user may have provided their own)
      lastC = index;
    }
  });

  args.splice(lastC, 0, c);
}

/**
 * Invokes `console.log()` when available.
 * No-op when `console.log` is not a "function".
 *
 * @api public
 */

function log() {
  // this hackery is required for IE8/9, where
  // the `console.log` function doesn't have 'apply'
  return 'object' === typeof console
    && console.log
    && Function.prototype.apply.call(console.log, console, arguments);
}

/**
 * Save `namespaces`.
 *
 * @param {String} namespaces
 * @api private
 */

function save(namespaces) {
  try {
    if (null == namespaces) {
      exports.storage.removeItem('debug');
    } else {
      exports.storage.debug = namespaces;
    }
  } catch(e) {}
}

/**
 * Load `namespaces`.
 *
 * @return {String} returns the previously persisted debug modes
 * @api private
 */

function load() {
  var r;
  try {
    r = exports.storage.debug;
  } catch(e) {}

  // If debug isn't set in LS, and we're in Electron, try to load $DEBUG
  if (!r && typeof process !== 'undefined' && 'env' in process) {
    r = process.env.DEBUG;
  }

  return r;
}

/**
 * Enable namespaces listed in `localStorage.debug` initially.
 */

exports.enable(load());

/**
 * Localstorage attempts to return the localstorage.
 *
 * This is necessary because safari throws
 * when a user disables cookies/localstorage
 * and you attempt to access it.
 *
 * @return {LocalStorage}
 * @api private
 */

function localstorage() {
  try {
    return window.localStorage;
  } catch (e) {}
}

/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(8)))

/***/ }),
/* 8 */
/***/ (function(module, exports) {

// shim for using process in browser
var process = module.exports = {};

// cached from whatever global is present so that test runners that stub it
// don't break things.  But we need to wrap it in a try catch in case it is
// wrapped in strict mode code which doesn't define any globals.  It's inside a
// function because try/catches deoptimize in certain engines.

var cachedSetTimeout;
var cachedClearTimeout;

function defaultSetTimout() {
    throw new Error('setTimeout has not been defined');
}
function defaultClearTimeout () {
    throw new Error('clearTimeout has not been defined');
}
(function () {
    try {
        if (typeof setTimeout === 'function') {
            cachedSetTimeout = setTimeout;
        } else {
            cachedSetTimeout = defaultSetTimout;
        }
    } catch (e) {
        cachedSetTimeout = defaultSetTimout;
    }
    try {
        if (typeof clearTimeout === 'function') {
            cachedClearTimeout = clearTimeout;
        } else {
            cachedClearTimeout = defaultClearTimeout;
        }
    } catch (e) {
        cachedClearTimeout = defaultClearTimeout;
    }
} ())
function runTimeout(fun) {
    if (cachedSetTimeout === setTimeout) {
        //normal enviroments in sane situations
        return setTimeout(fun, 0);
    }
    // if setTimeout wasn't available but was latter defined
    if ((cachedSetTimeout === defaultSetTimout || !cachedSetTimeout) && setTimeout) {
        cachedSetTimeout = setTimeout;
        return setTimeout(fun, 0);
    }
    try {
        // when when somebody has screwed with setTimeout but no I.E. maddness
        return cachedSetTimeout(fun, 0);
    } catch(e){
        try {
            // When we are in I.E. but the script has been evaled so I.E. doesn't trust the global object when called normally
            return cachedSetTimeout.call(null, fun, 0);
        } catch(e){
            // same as above but when it's a version of I.E. that must have the global object for 'this', hopfully our context correct otherwise it will throw a global error
            return cachedSetTimeout.call(this, fun, 0);
        }
    }


}
function runClearTimeout(marker) {
    if (cachedClearTimeout === clearTimeout) {
        //normal enviroments in sane situations
        return clearTimeout(marker);
    }
    // if clearTimeout wasn't available but was latter defined
    if ((cachedClearTimeout === defaultClearTimeout || !cachedClearTimeout) && clearTimeout) {
        cachedClearTimeout = clearTimeout;
        return clearTimeout(marker);
    }
    try {
        // when when somebody has screwed with setTimeout but no I.E. maddness
        return cachedClearTimeout(marker);
    } catch (e){
        try {
            // When we are in I.E. but the script has been evaled so I.E. doesn't  trust the global object when called normally
            return cachedClearTimeout.call(null, marker);
        } catch (e){
            // same as above but when it's a version of I.E. that must have the global object for 'this', hopfully our context correct otherwise it will throw a global error.
            // Some versions of I.E. have different rules for clearTimeout vs setTimeout
            return cachedClearTimeout.call(this, marker);
        }
    }



}
var queue = [];
var draining = false;
var currentQueue;
var queueIndex = -1;

function cleanUpNextTick() {
    if (!draining || !currentQueue) {
        return;
    }
    draining = false;
    if (currentQueue.length) {
        queue = currentQueue.concat(queue);
    } else {
        queueIndex = -1;
    }
    if (queue.length) {
        drainQueue();
    }
}

function drainQueue() {
    if (draining) {
        return;
    }
    var timeout = runTimeout(cleanUpNextTick);
    draining = true;

    var len = queue.length;
    while(len) {
        currentQueue = queue;
        queue = [];
        while (++queueIndex < len) {
            if (currentQueue) {
                currentQueue[queueIndex].run();
            }
        }
        queueIndex = -1;
        len = queue.length;
    }
    currentQueue = null;
    draining = false;
    runClearTimeout(timeout);
}

process.nextTick = function (fun) {
    var args = new Array(arguments.length - 1);
    if (arguments.length > 1) {
        for (var i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }
    }
    queue.push(new Item(fun, args));
    if (queue.length === 1 && !draining) {
        runTimeout(drainQueue);
    }
};

// v8 likes predictible objects
function Item(fun, array) {
    this.fun = fun;
    this.array = array;
}
Item.prototype.run = function () {
    this.fun.apply(null, this.array);
};
process.title = 'browser';
process.browser = true;
process.env = {};
process.argv = [];
process.version = ''; // empty string to avoid regexp issues
process.versions = {};

function noop() {}

process.on = noop;
process.addListener = noop;
process.once = noop;
process.off = noop;
process.removeListener = noop;
process.removeAllListeners = noop;
process.emit = noop;
process.prependListener = noop;
process.prependOnceListener = noop;

process.listeners = function (name) { return [] }

process.binding = function (name) {
    throw new Error('process.binding is not supported');
};

process.cwd = function () { return '/' };
process.chdir = function (dir) {
    throw new Error('process.chdir is not supported');
};
process.umask = function() { return 0; };


/***/ }),
/* 9 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _RectF = __webpack_require__(46);

var _RectF2 = _interopRequireDefault(_RectF);

var _Options = __webpack_require__(15);

var _Options2 = _interopRequireDefault(_Options);

var _EntitySet = __webpack_require__(45);

var _EntitySet2 = _interopRequireDefault(_EntitySet);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var BaseRender = function () {
    function BaseRender(name, option, readyFn) {
        _classCallCheck(this, BaseRender);

        this.baseChart = null;
        this.drawings = new Array();
        this.entitySet = new _EntitySet2.default(name, option);
        this.unitX = 0;
        this.unitY = 0;
        this.xAxisCount = 0;
        this.yAxisCount = 0;
        this.padding = [0, 0, 0, 0];
        this.margin = [0, 0, 0, 0];
        this.option = option ? option : new _Options2.default();
        this.viewport = new _RectF2.default(0, 0, 0, 0);
        this.drawViewPort = new _RectF2.default(0, 0, 0, 0);
        this.name = name;

        this.scaleX = 1;
        this.scaleY = 1;

        this.startX = 0;
        this.startY = 0;

        this.dx = 0;
        this.dy = 0;

        // this.totalDx = 0
        // this.totalDy = 0

        this._totalDx = 0;
        this._totalDy = 0;

        this.drawViewPortMax_H = 0;
        this.drawViewPortMin_L = 0;

        this.readyFn = readyFn;

        this.checkBounds = true;

        this.timeOutHandler = undefined;

        this.pageX = 0;
        this.pageY = 0;
    }

    _createClass(BaseRender, [{
        key: "touch",
        value: function touch(event) {
            var event = event || window.event;
            var touch = event.touches[0];
            if (touch) {
                this.pageX = touch.pageX;
                this.pageY = touch.pageY;
            }
            if (!this.inTouchBounds(event) && this.checkBounds) {
                return;
            }
            switch (event.type) {
                case "touchstart":
                    var touch = event.touches[0];
                    this.startX = touch.pageX;
                    this.startY = touch.pageY;
                    this.touchStart(event, this.startX, this.startY);
                    break;
                case "touchend":
                case "touchcancel":
                    this.touchEnd(event);
                    this.dx = 0;
                    this.dy = 0;
                    break;
                case "touchmove":
                    var touch = event.touches[0];
                    this.dx = touch.pageX - this.startX;
                    this.dy = touch.pageY - this.startY;
                    this.pageX = touch.pageX;
                    this.pageY = touch.pageY;
                    this.touchMove(event, touch.pageX, touch.pageY, this.dx, this.dy);
                    break;
            }
            this.refresh();
        }
    }, {
        key: "inTouchBounds",
        value: function inTouchBounds(event) {
            var startX = this.pageX;
            var startY = this.pageY;
            var left = this.baseChart.left + this.viewport.left;
            var top = this.baseChart.top + this.viewport.top;
            if (startX >= left && startX <= left + this.viewport.right && startY >= top && startY <= top + this.viewport.bottom) {
                return true;
            } else {
                return false;
            }
        }
    }, {
        key: "refresh",
        value: function refresh() {
            this.entitySet.startIndex = Math.max(this.getX(0) - 1, 0);
            this.entitySet.endIndex = Math.min(this.getX(this.drawViewPort.right) - 1, this.entitySet.length()
            // console.log(`startIndex = ${this.entitySet.startIndex} , endIndex = ${this.entitySet.endIndex} unitX = ${this.unitX} unitY = ${this.unitY}`)
            // this.baseChart.invalidate()

            );
        }
    }, {
        key: "ready",
        value: function ready() {
            if (this.readyFn instanceof Function) {
                this.readyFn();
            }
        }
    }, {
        key: "setReadyFn",
        value: function setReadyFn(fn) {
            this.readyFn = fn;
        }
    }, {
        key: "touchStart",
        value: function touchStart(event, startX, startY) {
            var _this = this;

            this._totalDx = this.entitySet.totalDx;
            this._totalDy = this.entitySet.totalDy;

            this.entitySet.hightlightX = -startX;
            this.entitySet.hightlightY = startY;

            this.timeOutHandler = setTimeout(function () {
                if (_this.dx == 0) {
                    _this.entitySet.hightlightShow = true;
                    _this.baseChart.invalidate();
                }
            }, 1500
            // console.log(`startX = ${startX} ,startY = ${startY} index = ${this.getX(startX)} price = ${this.getY(startY)} startIndex = ${this.entitySet.startIndex} endIndex = ${this.entitySet.endIndex} maxIndex = ${this.entitySet.getMaxIndex()} minIndex = ${this.entitySet.getMinIndex()}`)
            );
        }
    }, {
        key: "touchMove",
        value: function touchMove(event, pageX, pageY, dx, dy) {
            this.entitySet.hightlightX = -pageX;
            this.entitySet.hightlightY = pageY;
            if (this.entitySet.hightlightShow) {
                return;
            }
            if (this._totalDx + this.dx <= -this.entitySet.option.klineWidth / 2 && this._totalDx + this.dx >= -this.unitX * (this.entitySet.length() - this.xAxisCount + 1) + this.entitySet.option.klineWidth / 2) {
                this.entitySet.totalDx = this._totalDx + this.dx;
                this.entitySet.totalDy = this._totalDy + this.dy;
            }

            // console.log(`dx = ${dx} , dy = ${dy} totalDx = ${this.entitySet.totalDx}`)
        }
    }, {
        key: "touchEnd",
        value: function touchEnd(event) {
            var kw = this.entitySet.option.klineWidth + this.entitySet.option.klineGap;
            if (this.dx != 0) {
                this.entitySet.totalDx = Number.parseInt(this.entitySet.totalDx / kw) * kw - kw / 2;
            }
            if (this.timeOutHandler) {
                clearTimeout(this.timeOutHandler);
                this.timeOutHandler = undefined;
            }
            this.entitySet.hightlightShow = false;
            // console.log(`totalDx = ${this.entitySet.totalDx}`)
        }
    }, {
        key: "addToEnd",
        value: function addToEnd(e) {
            this.entitySet.push(e);
        }
    }, {
        key: "addToFront",
        value: function addToFront(e) {
            // let len = this.entitySet.length()
            this.entitySet.unshift(e
            // len = this.entitySet.length() - len
            // this.entitySet.startIndex += len
            // this.entitySet.endIndex += len
            );
        }
    }, {
        key: "addDrawing",
        value: function addDrawing(drawing) {
            this.drawings.push(drawing);
        }
    }, {
        key: "removeDrawing",
        value: function removeDrawing(drawing) {
            if (this.drawings.has(drawing)) {
                this.drawings.delete(drawing);
            }
        }
    }, {
        key: "clearDrawing",
        value: function clearDrawing() {
            this.drawings.clear();
        }
    }, {
        key: "order",
        value: function order() {
            this.drawings.sort(function (a, b) {
                if (a.getOrder() > b.getOrder()) {
                    return 1;
                } else if (a.getOrder() < b.getOrder()) {
                    return -1;
                } else {
                    return 0;
                }
            });
        }
    }, {
        key: "init",
        value: function init(chart) {
            this.order();
            this.initAxisCount();
            this.firstInitViewPortIndex();
            this.initUnit();
            this.firstInitPosition();
            this.baseChart = chart;
            this.clearRender();
        }
    }, {
        key: "firstInitViewPortIndex",
        value: function firstInitViewPortIndex() {
            if (this.entitySet.length() <= this.xAxisCount) {
                this.entitySet.startIndex = 0;
                this.entitySet.endIndex = Math.min(this.entitySet.length(), this.xAxisCount);
            } else {
                this.entitySet.startIndex = Math.max(this.entitySet.length() - this.xAxisCount, 0);
                this.entitySet.endIndex = this.entitySet.length();
            }
        }
    }, {
        key: "firstInitPosition",
        value: function firstInitPosition() {
            if (this.entitySet.totalDx == 0) {
                this.entitySet.totalDx = this.getXPixel(this.entitySet.startIndex);
                if (this.entitySet.length() < this.xAxisCount) {
                    this.entitySet.totalDx += (this.entitySet.option.klineWidth + this.entitySet.option.klineGap) / 2;
                }
            }
        }
    }, {
        key: "clearRender",
        value: function clearRender() {
            this.baseChart.canvasCxt.save();
            this.baseChart.canvasCxt.fillStyle = this.option.background;
            this.baseChart.canvasCxt.fillRect(this.drawViewPort.left, this.drawViewPort.top, this.drawViewPort.right, this.drawViewPort.bottom);
            this.baseChart.canvasCxt.restore();
        }
    }, {
        key: "initAxisCount",
        value: function initAxisCount() {
            this.xAxisCount = Number.parseInt(this.drawViewPort.right / (this.option.klineWidth + this.option.klineGap));
            this.yAxisCount = 3;
        }
    }, {
        key: "initUnit",
        value: function initUnit() {
            if (this.entitySet.length() <= 0) {
                return;
            }
            var h = Number.parseFloat(this.entitySet.getMaxEntity().h);
            var l = Number.parseFloat(this.entitySet.getMinEntity().l);
            var tempUnitY = this.drawViewPort.bottom / (h - l);
            this.drawViewPortMax_H = h + this.padding[1] / tempUnitY;
            this.drawViewPortMin_L = l - this.padding[3] / tempUnitY;
            this.dy = this.drawViewPortMax_H - this.drawViewPortMin_L;
            this.unitX = this.option.klineWidth + this.option.klineGap;
            this.unitY = this.dy / this.drawViewPort.bottom; // unit/pix
        }
    }, {
        key: "getXPixel",
        value: function getXPixel(x) {
            x = Number.parseFloat(x);
            return Math.ceil(-(this.unitX * x) - this.entitySet.totalDx);
        }
    }, {
        key: "getYPixel",
        value: function getYPixel(y) {
            y = Number.parseFloat(y);
            return Math.ceil((y - Number.parseFloat(this.drawViewPortMin_L)) / this.unitY);
        }
    }, {
        key: "getX",
        value: function getX(pix) {
            return Math.ceil((pix - this.entitySet.totalDx) / this.unitX);
        }
    }, {
        key: "getY",
        value: function getY(pix) {
            return (pix - this.entitySet.totalDy) * this.unitY + Number.parseFloat(this.drawViewPortMin_L);
        }
    }]);

    return BaseRender;
}();

exports.default = BaseRender;

/***/ }),
/* 10 */
/***/ (function(module, exports, __webpack_require__) {

/**
 * Module dependencies.
 */

var parser = __webpack_require__(2);
var Emitter = __webpack_require__(1);

/**
 * Module exports.
 */

module.exports = Transport;

/**
 * Transport abstract constructor.
 *
 * @param {Object} options.
 * @api private
 */

function Transport (opts) {
  this.path = opts.path;
  this.hostname = opts.hostname;
  this.port = opts.port;
  this.secure = opts.secure;
  this.query = opts.query;
  this.timestampParam = opts.timestampParam;
  this.timestampRequests = opts.timestampRequests;
  this.readyState = '';
  this.agent = opts.agent || false;
  this.socket = opts.socket;
  this.enablesXDR = opts.enablesXDR;

  // SSL options for Node.js client
  this.pfx = opts.pfx;
  this.key = opts.key;
  this.passphrase = opts.passphrase;
  this.cert = opts.cert;
  this.ca = opts.ca;
  this.ciphers = opts.ciphers;
  this.rejectUnauthorized = opts.rejectUnauthorized;
  this.forceNode = opts.forceNode;

  // other options for Node.js client
  this.extraHeaders = opts.extraHeaders;
  this.localAddress = opts.localAddress;
}

/**
 * Mix in `Emitter`.
 */

Emitter(Transport.prototype);

/**
 * Emits an error.
 *
 * @param {String} str
 * @return {Transport} for chaining
 * @api public
 */

Transport.prototype.onError = function (msg, desc) {
  var err = new Error(msg);
  err.type = 'TransportError';
  err.description = desc;
  this.emit('error', err);
  return this;
};

/**
 * Opens the transport.
 *
 * @api public
 */

Transport.prototype.open = function () {
  if ('closed' === this.readyState || '' === this.readyState) {
    this.readyState = 'opening';
    this.doOpen();
  }

  return this;
};

/**
 * Closes the transport.
 *
 * @api private
 */

Transport.prototype.close = function () {
  if ('opening' === this.readyState || 'open' === this.readyState) {
    this.doClose();
    this.onClose();
  }

  return this;
};

/**
 * Sends multiple packets.
 *
 * @param {Array} packets
 * @api private
 */

Transport.prototype.send = function (packets) {
  if ('open' === this.readyState) {
    this.write(packets);
  } else {
    throw new Error('Transport not open');
  }
};

/**
 * Called upon open
 *
 * @api private
 */

Transport.prototype.onOpen = function () {
  this.readyState = 'open';
  this.writable = true;
  this.emit('open');
};

/**
 * Called with data.
 *
 * @param {String} data
 * @api private
 */

Transport.prototype.onData = function (data) {
  var packet = parser.decodePacket(data, this.socket.binaryType);
  this.onPacket(packet);
};

/**
 * Called with a decoded packet.
 */

Transport.prototype.onPacket = function (packet) {
  this.emit('packet', packet);
};

/**
 * Called upon close.
 *
 * @api private
 */

Transport.prototype.onClose = function () {
  this.readyState = 'closed';
  this.emit('close');
};


/***/ }),
/* 11 */
/***/ (function(module, exports, __webpack_require__) {

/* WEBPACK VAR INJECTION */(function(global) {// browser shim for xmlhttprequest module

var hasCORS = __webpack_require__(61);

module.exports = function (opts) {
  var xdomain = opts.xdomain;

  // scheme must be same when usign XDomainRequest
  // http://blogs.msdn.com/b/ieinternals/archive/2010/05/13/xdomainrequest-restrictions-limitations-and-workarounds.aspx
  var xscheme = opts.xscheme;

  // XDomainRequest has a flow of not sending cookie, therefore it should be disabled as a default.
  // https://github.com/Automattic/engine.io-client/pull/217
  var enablesXDR = opts.enablesXDR;

  // XMLHttpRequest can be disabled on IE
  try {
    if ('undefined' !== typeof XMLHttpRequest && (!xdomain || hasCORS)) {
      return new XMLHttpRequest();
    }
  } catch (e) { }

  // Use XDomainRequest for IE8 if enablesXDR is true
  // because loading bar keeps flashing when using jsonp-polling
  // https://github.com/yujiosaka/socke.io-ie8-loading-example
  try {
    if ('undefined' !== typeof XDomainRequest && !xscheme && enablesXDR) {
      return new XDomainRequest();
    }
  } catch (e) { }

  if (!xdomain) {
    try {
      return new global[['Active'].concat('Object').join('X')]('Microsoft.XMLHTTP');
    } catch (e) { }
  }
};

/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(0)))

/***/ }),
/* 12 */
/***/ (function(module, exports, __webpack_require__) {


/**
 * Module dependencies.
 */

var debug = __webpack_require__(67)('socket.io-parser');
var Emitter = __webpack_require__(1);
var hasBin = __webpack_require__(19);
var binary = __webpack_require__(66);
var isBuf = __webpack_require__(26);

/**
 * Protocol version.
 *
 * @api public
 */

exports.protocol = 4;

/**
 * Packet types.
 *
 * @api public
 */

exports.types = [
  'CONNECT',
  'DISCONNECT',
  'EVENT',
  'ACK',
  'ERROR',
  'BINARY_EVENT',
  'BINARY_ACK'
];

/**
 * Packet type `connect`.
 *
 * @api public
 */

exports.CONNECT = 0;

/**
 * Packet type `disconnect`.
 *
 * @api public
 */

exports.DISCONNECT = 1;

/**
 * Packet type `event`.
 *
 * @api public
 */

exports.EVENT = 2;

/**
 * Packet type `ack`.
 *
 * @api public
 */

exports.ACK = 3;

/**
 * Packet type `error`.
 *
 * @api public
 */

exports.ERROR = 4;

/**
 * Packet type 'binary event'
 *
 * @api public
 */

exports.BINARY_EVENT = 5;

/**
 * Packet type `binary ack`. For acks with binary arguments.
 *
 * @api public
 */

exports.BINARY_ACK = 6;

/**
 * Encoder constructor.
 *
 * @api public
 */

exports.Encoder = Encoder;

/**
 * Decoder constructor.
 *
 * @api public
 */

exports.Decoder = Decoder;

/**
 * A socket.io Encoder instance
 *
 * @api public
 */

function Encoder() {}

/**
 * Encode a packet as a single string if non-binary, or as a
 * buffer sequence, depending on packet type.
 *
 * @param {Object} obj - packet object
 * @param {Function} callback - function to handle encodings (likely engine.write)
 * @return Calls callback with Array of encodings
 * @api public
 */

Encoder.prototype.encode = function(obj, callback){
  if ((obj.type === exports.EVENT || obj.type === exports.ACK) && hasBin(obj.data)) {
    obj.type = obj.type === exports.EVENT ? exports.BINARY_EVENT : exports.BINARY_ACK;
  }

  debug('encoding packet %j', obj);

  if (exports.BINARY_EVENT === obj.type || exports.BINARY_ACK === obj.type) {
    encodeAsBinary(obj, callback);
  }
  else {
    var encoding = encodeAsString(obj);
    callback([encoding]);
  }
};

/**
 * Encode packet as string.
 *
 * @param {Object} packet
 * @return {String} encoded
 * @api private
 */

function encodeAsString(obj) {

  // first is type
  var str = '' + obj.type;

  // attachments if we have them
  if (exports.BINARY_EVENT === obj.type || exports.BINARY_ACK === obj.type) {
    str += obj.attachments + '-';
  }

  // if we have a namespace other than `/`
  // we append it followed by a comma `,`
  if (obj.nsp && '/' !== obj.nsp) {
    str += obj.nsp + ',';
  }

  // immediately followed by the id
  if (null != obj.id) {
    str += obj.id;
  }

  // json data
  if (null != obj.data) {
    str += JSON.stringify(obj.data);
  }

  debug('encoded %j as %s', obj, str);
  return str;
}

/**
 * Encode packet as 'buffer sequence' by removing blobs, and
 * deconstructing packet into object with placeholders and
 * a list of buffers.
 *
 * @param {Object} packet
 * @return {Buffer} encoded
 * @api private
 */

function encodeAsBinary(obj, callback) {

  function writeEncoding(bloblessData) {
    var deconstruction = binary.deconstructPacket(bloblessData);
    var pack = encodeAsString(deconstruction.packet);
    var buffers = deconstruction.buffers;

    buffers.unshift(pack); // add packet info to beginning of data list
    callback(buffers); // write all the buffers
  }

  binary.removeBlobs(obj, writeEncoding);
}

/**
 * A socket.io Decoder instance
 *
 * @return {Object} decoder
 * @api public
 */

function Decoder() {
  this.reconstructor = null;
}

/**
 * Mix in `Emitter` with Decoder.
 */

Emitter(Decoder.prototype);

/**
 * Decodes an ecoded packet string into packet JSON.
 *
 * @param {String} obj - encoded packet
 * @return {Object} packet
 * @api public
 */

Decoder.prototype.add = function(obj) {
  var packet;
  if (typeof obj === 'string') {
    packet = decodeString(obj);
    if (exports.BINARY_EVENT === packet.type || exports.BINARY_ACK === packet.type) { // binary packet's json
      this.reconstructor = new BinaryReconstructor(packet);

      // no attachments, labeled binary but no binary data to follow
      if (this.reconstructor.reconPack.attachments === 0) {
        this.emit('decoded', packet);
      }
    } else { // non-binary full packet
      this.emit('decoded', packet);
    }
  }
  else if (isBuf(obj) || obj.base64) { // raw binary data
    if (!this.reconstructor) {
      throw new Error('got binary data when not reconstructing a packet');
    } else {
      packet = this.reconstructor.takeBinaryData(obj);
      if (packet) { // received final buffer
        this.reconstructor = null;
        this.emit('decoded', packet);
      }
    }
  }
  else {
    throw new Error('Unknown type: ' + obj);
  }
};

/**
 * Decode a packet String (JSON data)
 *
 * @param {String} str
 * @return {Object} packet
 * @api private
 */

function decodeString(str) {
  var i = 0;
  // look up type
  var p = {
    type: Number(str.charAt(0))
  };

  if (null == exports.types[p.type]) return error();

  // look up attachments if type binary
  if (exports.BINARY_EVENT === p.type || exports.BINARY_ACK === p.type) {
    var buf = '';
    while (str.charAt(++i) !== '-') {
      buf += str.charAt(i);
      if (i == str.length) break;
    }
    if (buf != Number(buf) || str.charAt(i) !== '-') {
      throw new Error('Illegal attachments');
    }
    p.attachments = Number(buf);
  }

  // look up namespace (if any)
  if ('/' === str.charAt(i + 1)) {
    p.nsp = '';
    while (++i) {
      var c = str.charAt(i);
      if (',' === c) break;
      p.nsp += c;
      if (i === str.length) break;
    }
  } else {
    p.nsp = '/';
  }

  // look up id
  var next = str.charAt(i + 1);
  if ('' !== next && Number(next) == next) {
    p.id = '';
    while (++i) {
      var c = str.charAt(i);
      if (null == c || Number(c) != c) {
        --i;
        break;
      }
      p.id += str.charAt(i);
      if (i === str.length) break;
    }
    p.id = Number(p.id);
  }

  // look up json data
  if (str.charAt(++i)) {
    p = tryParse(p, str.substr(i));
  }

  debug('decoded %s as %j', str, p);
  return p;
}

function tryParse(p, str) {
  try {
    p.data = JSON.parse(str);
  } catch(e){
    return error();
  }
  return p; 
}

/**
 * Deallocates a parser's resources
 *
 * @api public
 */

Decoder.prototype.destroy = function() {
  if (this.reconstructor) {
    this.reconstructor.finishedReconstruction();
  }
};

/**
 * A manager of a binary event's 'buffer sequence'. Should
 * be constructed whenever a packet of type BINARY_EVENT is
 * decoded.
 *
 * @param {Object} packet
 * @return {BinaryReconstructor} initialized reconstructor
 * @api private
 */

function BinaryReconstructor(packet) {
  this.reconPack = packet;
  this.buffers = [];
}

/**
 * Method to be called when binary data received from connection
 * after a BINARY_EVENT packet.
 *
 * @param {Buffer | ArrayBuffer} binData - the raw binary data received
 * @return {null | Object} returns null if more binary data is expected or
 *   a reconstructed packet object if all buffers have been received.
 * @api private
 */

BinaryReconstructor.prototype.takeBinaryData = function(binData) {
  this.buffers.push(binData);
  if (this.buffers.length === this.reconPack.attachments) { // done with buffer list
    var packet = binary.reconstructPacket(this.reconPack, this.buffers);
    this.finishedReconstruction();
    return packet;
  }
  return null;
};

/**
 * Cleans up binary packet reconstruction variables.
 *
 * @api private
 */

BinaryReconstructor.prototype.finishedReconstruction = function() {
  this.reconPack = null;
  this.buffers = [];
};

function error() {
  return {
    type: exports.ERROR,
    data: 'parser error'
  };
}


/***/ }),
/* 13 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _BaseRender2 = __webpack_require__(9);

var _BaseRender3 = _interopRequireDefault(_BaseRender2);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var TimeRender = function (_BaseRender) {
    _inherits(TimeRender, _BaseRender);

    function TimeRender(period, name, option) {
        _classCallCheck(this, TimeRender);

        return _possibleConstructorReturn(this, (TimeRender.__proto__ || Object.getPrototypeOf(TimeRender)).call(this, name, option));
    }

    _createClass(TimeRender, [{
        key: "initUnit",
        value: function initUnit() {
            if (this.entitySet.length() <= 0) {
                return;
            }
            var h = Number.parseFloat(this.entitySet.getMaxEntity().h);
            var l = Number.parseFloat(this.entitySet.getMinEntity().l);
            var tempUnitY = this.drawViewPort.bottom / (h - l);
            this.drawViewPortMax_H = h + this.padding[1] / tempUnitY;
            this.drawViewPortMin_L = l - this.padding[3] / tempUnitY;
            this.dy = this.drawViewPortMax_H - this.drawViewPortMin_L;
            this.unitX = this.drawViewPort.right / 240; //pix/min
            this.unitY = this.dy / this.drawViewPort.bottom; // unit/pix
        }
    }, {
        key: "firstInitViewPortIndex",
        value: function firstInitViewPortIndex() {
            this.entitySet.startIndex = 0;
            this.entitySet.endIndex = this.entitySet.length();
        }
    }, {
        key: "firstInitPosition",
        value: function firstInitPosition() {
            this.entitySet.totalDx = 0;
        }
    }, {
        key: "addToEnd",
        value: function addToEnd(e) {
            this.entitySet.push(e);
            this.firstInitViewPortIndex();
            this.refresh();
        }
    }, {
        key: "refresh",
        value: function refresh() {
            // if (this.baseChart) {
            //     this.baseChart.invalidate()
            // }
        }
    }, {
        key: "touchStart",
        value: function touchStart(event, startX, startY) {}
    }, {
        key: "touchMove",
        value: function touchMove(event, pageX, pageY, dx, dy) {}
    }, {
        key: "touchEnd",
        value: function touchEnd(event) {}
    }, {
        key: "minuteToTime",
        value: function minuteToTime(mint) {
            var h = mint / 60;
            var m = mint % 60;
            if (h < 10) {
                h = "0" + h;
            }
            if (m < 10) {
                m = "0" + m;
            }
            return [h, m];
        }
    }]);

    return TimeRender;
}(_BaseRender3.default);

exports.default = TimeRender;

/***/ }),
/* 14 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var Entity = function Entity(x, y, title, desc, width, height, option) {
    _classCallCheck(this, Entity);

    this.x = x;
    this.y = y;
    this.title = title;
    this.desc = desc;
    this.width = width;
    this.height = height;
    this.option = option;
};

exports.default = Entity;

/***/ }),
/* 15 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var Options = function Options(strokeColor, strokeWidth, solid, fillColor, upColor, downColor, upLineColor, downLineColor, kShadowColor, lineColor, lineWidth, klineWidth, klineGap, opacity, weight, background, shadowBlur, shadowColor, shadowOffsetX, shadowOffsetY, textColor, textFont, textPosition, textAlign, globalAlpha) {
    _classCallCheck(this, Options);

    this.strokeColor = strokeColor ? strokeColor : '#000000';
    this.strokeWidth = strokeWidth ? strokeWidth : 1;
    this.solid = solid ? solid : true;
    this.fillColor = fillColor ? fillColor : '#ec3333';
    this.upColor = upColor ? upColor : '#ec3333';
    this.downColor = downColor ? downColor : '#0a9650';
    this.klineWidth = klineWidth ? klineWidth : 6;
    this.klineGap = klineGap ? klineGap : 1;
    this.lineColor = lineColor ? lineColor : '#508ce7';
    this.lineWidth = lineWidth ? lineWidth : 1;
    this.upLineColor = upLineColor ? upLineColor : '#ec3333';
    this.downLineColor = downLineColor ? downLineColor : '#0a9650';
    this.opacity = opacity ? opacity : 1;
    this.shadowBlur = shadowBlur ? shadowBlur : 0;
    this.shadowColor = shadowColor ? shadowColor : 'rgba(245, 246, 248, 0.5)';
    this.shadowOffsetX = shadowOffsetX ? shadowOffsetX : 0;
    this.shadowOffsetY = shadowOffsetY ? shadowOffsetY : 0;
    this.textColor = textColor ? textColor : '#333333';
    this.textFont = textFont ? textFont : 'bold 18px verdana';
    this.textPosition = textPosition ? textPosition : 'end'; //inside, left, right, top, bottom
    this.textAlign = textAlign ? textAlign : 'center';
    this.kShadowColor = kShadowColor ? kShadowColor : '"black"';
    this.background = background ? background : '#ffffff';
    this.weight = weight ? weight : 0;
    this.globalAlpha = globalAlpha ? globalAlpha : 1;
};

exports.default = Options;

/***/ }),
/* 16 */
/***/ (function(module, exports) {

/**
 * Slice reference.
 */

var slice = [].slice;

/**
 * Bind `obj` to `fn`.
 *
 * @param {Object} obj
 * @param {Function|String} fn or string
 * @return {Function}
 * @api public
 */

module.exports = function(obj, fn){
  if ('string' == typeof fn) fn = obj[fn];
  if ('function' != typeof fn) throw new Error('bind() requires a function');
  var args = slice.call(arguments, 2);
  return function(){
    return fn.apply(obj, args.concat(slice.call(arguments)));
  }
};


/***/ }),
/* 17 */
/***/ (function(module, exports, __webpack_require__) {

/* WEBPACK VAR INJECTION */(function(global) {/**
 * Module dependencies
 */

var XMLHttpRequest = __webpack_require__(11);
var XHR = __webpack_require__(55);
var JSONP = __webpack_require__(54);
var websocket = __webpack_require__(56);

/**
 * Export transports.
 */

exports.polling = polling;
exports.websocket = websocket;

/**
 * Polling transport polymorphic constructor.
 * Decides on xhr vs jsonp based on feature detection.
 *
 * @api private
 */

function polling (opts) {
  var xhr;
  var xd = false;
  var xs = false;
  var jsonp = false !== opts.jsonp;

  if (global.location) {
    var isSSL = 'https:' === location.protocol;
    var port = location.port;

    // some user agents have empty `location.port`
    if (!port) {
      port = isSSL ? 443 : 80;
    }

    xd = opts.hostname !== location.hostname || port !== opts.port;
    xs = opts.secure !== isSSL;
  }

  opts.xdomain = xd;
  opts.xscheme = xs;
  xhr = new XMLHttpRequest(opts);

  if ('open' in xhr && !opts.forceJSONP) {
    return new XHR(opts);
  } else {
    if (!jsonp) throw new Error('JSONP disabled');
    return new JSONP(opts);
  }
}

/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(0)))

/***/ }),
/* 18 */
/***/ (function(module, exports, __webpack_require__) {

/**
 * Module dependencies.
 */

var Transport = __webpack_require__(10);
var parseqs = __webpack_require__(6);
var parser = __webpack_require__(2);
var inherit = __webpack_require__(4);
var yeast = __webpack_require__(27);
var debug = __webpack_require__(5)('engine.io-client:polling');

/**
 * Module exports.
 */

module.exports = Polling;

/**
 * Is XHR2 supported?
 */

var hasXHR2 = (function () {
  var XMLHttpRequest = __webpack_require__(11);
  var xhr = new XMLHttpRequest({ xdomain: false });
  return null != xhr.responseType;
})();

/**
 * Polling interface.
 *
 * @param {Object} opts
 * @api private
 */

function Polling (opts) {
  var forceBase64 = (opts && opts.forceBase64);
  if (!hasXHR2 || forceBase64) {
    this.supportsBinary = false;
  }
  Transport.call(this, opts);
}

/**
 * Inherits from Transport.
 */

inherit(Polling, Transport);

/**
 * Transport name.
 */

Polling.prototype.name = 'polling';

/**
 * Opens the socket (triggers polling). We write a PING message to determine
 * when the transport is open.
 *
 * @api private
 */

Polling.prototype.doOpen = function () {
  this.poll();
};

/**
 * Pauses polling.
 *
 * @param {Function} callback upon buffers are flushed and transport is paused
 * @api private
 */

Polling.prototype.pause = function (onPause) {
  var self = this;

  this.readyState = 'pausing';

  function pause () {
    debug('paused');
    self.readyState = 'paused';
    onPause();
  }

  if (this.polling || !this.writable) {
    var total = 0;

    if (this.polling) {
      debug('we are currently polling - waiting to pause');
      total++;
      this.once('pollComplete', function () {
        debug('pre-pause polling complete');
        --total || pause();
      });
    }

    if (!this.writable) {
      debug('we are currently writing - waiting to pause');
      total++;
      this.once('drain', function () {
        debug('pre-pause writing complete');
        --total || pause();
      });
    }
  } else {
    pause();
  }
};

/**
 * Starts polling cycle.
 *
 * @api public
 */

Polling.prototype.poll = function () {
  debug('polling');
  this.polling = true;
  this.doPoll();
  this.emit('poll');
};

/**
 * Overloads onData to detect payloads.
 *
 * @api private
 */

Polling.prototype.onData = function (data) {
  var self = this;
  debug('polling got data %s', data);
  var callback = function (packet, index, total) {
    // if its the first message we consider the transport open
    if ('opening' === self.readyState) {
      self.onOpen();
    }

    // if its a close packet, we close the ongoing requests
    if ('close' === packet.type) {
      self.onClose();
      return false;
    }

    // otherwise bypass onData and handle the message
    self.onPacket(packet);
  };

  // decode payload
  parser.decodePayload(data, this.socket.binaryType, callback);

  // if an event did not trigger closing
  if ('closed' !== this.readyState) {
    // if we got data we're not polling
    this.polling = false;
    this.emit('pollComplete');

    if ('open' === this.readyState) {
      this.poll();
    } else {
      debug('ignoring poll - transport state "%s"', this.readyState);
    }
  }
};

/**
 * For polling, send a close packet.
 *
 * @api private
 */

Polling.prototype.doClose = function () {
  var self = this;

  function close () {
    debug('writing close packet');
    self.write([{ type: 'close' }]);
  }

  if ('open' === this.readyState) {
    debug('transport open - closing');
    close();
  } else {
    // in case we're trying to close while
    // handshaking is in progress (GH-164)
    debug('transport not open - deferring close');
    this.once('open', close);
  }
};

/**
 * Writes a packets payload.
 *
 * @param {Array} data packets
 * @param {Function} drain callback
 * @api private
 */

Polling.prototype.write = function (packets) {
  var self = this;
  this.writable = false;
  var callbackfn = function () {
    self.writable = true;
    self.emit('drain');
  };

  parser.encodePayload(packets, this.supportsBinary, function (data) {
    self.doWrite(data, callbackfn);
  });
};

/**
 * Generates uri for connection.
 *
 * @api private
 */

Polling.prototype.uri = function () {
  var query = this.query || {};
  var schema = this.secure ? 'https' : 'http';
  var port = '';

  // cache busting is forced
  if (false !== this.timestampRequests) {
    query[this.timestampParam] = yeast();
  }

  if (!this.supportsBinary && !query.sid) {
    query.b64 = 1;
  }

  query = parseqs.encode(query);

  // avoid port if default for schema
  if (this.port && (('https' === schema && Number(this.port) !== 443) ||
     ('http' === schema && Number(this.port) !== 80))) {
    port = ':' + this.port;
  }

  // prepend ? to query
  if (query.length) {
    query = '?' + query;
  }

  var ipv6 = this.hostname.indexOf(':') !== -1;
  return schema + '://' + (ipv6 ? '[' + this.hostname + ']' : this.hostname) + port + this.path + query;
};


/***/ }),
/* 19 */
/***/ (function(module, exports, __webpack_require__) {

/* WEBPACK VAR INJECTION */(function(global) {/* global Blob File */

/*
 * Module requirements.
 */

var isArray = __webpack_require__(21);

var toString = Object.prototype.toString;
var withNativeBlob = typeof global.Blob === 'function' || toString.call(global.Blob) === '[object BlobConstructor]';
var withNativeFile = typeof global.File === 'function' || toString.call(global.File) === '[object FileConstructor]';

/**
 * Module exports.
 */

module.exports = hasBinary;

/**
 * Checks for binary data.
 *
 * Supports Buffer, ArrayBuffer, Blob and File.
 *
 * @param {Object} anything
 * @api public
 */

function hasBinary (obj) {
  if (!obj || typeof obj !== 'object') {
    return false;
  }

  if (isArray(obj)) {
    for (var i = 0, l = obj.length; i < l; i++) {
      if (hasBinary(obj[i])) {
        return true;
      }
    }
    return false;
  }

  if ((typeof global.Buffer === 'function' && global.Buffer.isBuffer && global.Buffer.isBuffer(obj)) ||
     (typeof global.ArrayBuffer === 'function' && obj instanceof ArrayBuffer) ||
     (withNativeBlob && obj instanceof Blob) ||
     (withNativeFile && obj instanceof File)
    ) {
    return true;
  }

  // see: https://github.com/Automattic/has-binary/pull/4
  if (obj.toJSON && typeof obj.toJSON === 'function' && arguments.length === 1) {
    return hasBinary(obj.toJSON(), true);
  }

  for (var key in obj) {
    if (Object.prototype.hasOwnProperty.call(obj, key) && hasBinary(obj[key])) {
      return true;
    }
  }

  return false;
}

/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(0)))

/***/ }),
/* 20 */
/***/ (function(module, exports) {


var indexOf = [].indexOf;

module.exports = function(arr, obj){
  if (indexOf) return arr.indexOf(obj);
  for (var i = 0; i < arr.length; ++i) {
    if (arr[i] === obj) return i;
  }
  return -1;
};

/***/ }),
/* 21 */
/***/ (function(module, exports) {

var toString = {}.toString;

module.exports = Array.isArray || function (arr) {
  return toString.call(arr) == '[object Array]';
};


/***/ }),
/* 22 */
/***/ (function(module, exports) {

/**
 * Parses an URI
 *
 * @author Steven Levithan <stevenlevithan.com> (MIT license)
 * @api private
 */

var re = /^(?:(?![^:@]+:[^:@\/]*@)(http|https|ws|wss):\/\/)?((?:(([^:@]*)(?::([^:@]*))?)?@)?((?:[a-f0-9]{0,4}:){2,7}[a-f0-9]{0,4}|[^:\/?#]*)(?::(\d*))?)(((\/(?:[^?#](?![^?#\/]*\.[^?#\/.]+(?:[?#]|$)))*\/?)?([^?#\/]*))(?:\?([^#]*))?(?:#(.*))?)/;

var parts = [
    'source', 'protocol', 'authority', 'userInfo', 'user', 'password', 'host', 'port', 'relative', 'path', 'directory', 'file', 'query', 'anchor'
];

module.exports = function parseuri(str) {
    var src = str,
        b = str.indexOf('['),
        e = str.indexOf(']');

    if (b != -1 && e != -1) {
        str = str.substring(0, b) + str.substring(b, e).replace(/:/g, ';') + str.substring(e, str.length);
    }

    var m = re.exec(str || ''),
        uri = {},
        i = 14;

    while (i--) {
        uri[parts[i]] = m[i] || '';
    }

    if (b != -1 && e != -1) {
        uri.source = src;
        uri.host = uri.host.substring(1, uri.host.length - 1).replace(/;/g, ':');
        uri.authority = uri.authority.replace('[', '').replace(']', '').replace(/;/g, ':');
        uri.ipv6uri = true;
    }

    return uri;
};


/***/ }),
/* 23 */
/***/ (function(module, exports, __webpack_require__) {


/**
 * Module dependencies.
 */

var eio = __webpack_require__(51);
var Socket = __webpack_require__(25);
var Emitter = __webpack_require__(1);
var parser = __webpack_require__(12);
var on = __webpack_require__(24);
var bind = __webpack_require__(16);
var debug = __webpack_require__(7)('socket.io-client:manager');
var indexOf = __webpack_require__(20);
var Backoff = __webpack_require__(48);

/**
 * IE6+ hasOwnProperty
 */

var has = Object.prototype.hasOwnProperty;

/**
 * Module exports
 */

module.exports = Manager;

/**
 * `Manager` constructor.
 *
 * @param {String} engine instance or engine uri/opts
 * @param {Object} options
 * @api public
 */

function Manager (uri, opts) {
  if (!(this instanceof Manager)) return new Manager(uri, opts);
  if (uri && ('object' === typeof uri)) {
    opts = uri;
    uri = undefined;
  }
  opts = opts || {};

  opts.path = opts.path || '/socket.io';
  this.nsps = {};
  this.subs = [];
  this.opts = opts;
  this.reconnection(opts.reconnection !== false);
  this.reconnectionAttempts(opts.reconnectionAttempts || Infinity);
  this.reconnectionDelay(opts.reconnectionDelay || 1000);
  this.reconnectionDelayMax(opts.reconnectionDelayMax || 5000);
  this.randomizationFactor(opts.randomizationFactor || 0.5);
  this.backoff = new Backoff({
    min: this.reconnectionDelay(),
    max: this.reconnectionDelayMax(),
    jitter: this.randomizationFactor()
  });
  this.timeout(null == opts.timeout ? 20000 : opts.timeout);
  this.readyState = 'closed';
  this.uri = uri;
  this.connecting = [];
  this.lastPing = null;
  this.encoding = false;
  this.packetBuffer = [];
  var _parser = opts.parser || parser;
  this.encoder = new _parser.Encoder();
  this.decoder = new _parser.Decoder();
  this.autoConnect = opts.autoConnect !== false;
  if (this.autoConnect) this.open();
}

/**
 * Propagate given event to sockets and emit on `this`
 *
 * @api private
 */

Manager.prototype.emitAll = function () {
  this.emit.apply(this, arguments);
  for (var nsp in this.nsps) {
    if (has.call(this.nsps, nsp)) {
      this.nsps[nsp].emit.apply(this.nsps[nsp], arguments);
    }
  }
};

/**
 * Update `socket.id` of all sockets
 *
 * @api private
 */

Manager.prototype.updateSocketIds = function () {
  for (var nsp in this.nsps) {
    if (has.call(this.nsps, nsp)) {
      this.nsps[nsp].id = this.generateId(nsp);
    }
  }
};

/**
 * generate `socket.id` for the given `nsp`
 *
 * @param {String} nsp
 * @return {String}
 * @api private
 */

Manager.prototype.generateId = function (nsp) {
  return (nsp === '/' ? '' : (nsp + '#')) + this.engine.id;
};

/**
 * Mix in `Emitter`.
 */

Emitter(Manager.prototype);

/**
 * Sets the `reconnection` config.
 *
 * @param {Boolean} true/false if it should automatically reconnect
 * @return {Manager} self or value
 * @api public
 */

Manager.prototype.reconnection = function (v) {
  if (!arguments.length) return this._reconnection;
  this._reconnection = !!v;
  return this;
};

/**
 * Sets the reconnection attempts config.
 *
 * @param {Number} max reconnection attempts before giving up
 * @return {Manager} self or value
 * @api public
 */

Manager.prototype.reconnectionAttempts = function (v) {
  if (!arguments.length) return this._reconnectionAttempts;
  this._reconnectionAttempts = v;
  return this;
};

/**
 * Sets the delay between reconnections.
 *
 * @param {Number} delay
 * @return {Manager} self or value
 * @api public
 */

Manager.prototype.reconnectionDelay = function (v) {
  if (!arguments.length) return this._reconnectionDelay;
  this._reconnectionDelay = v;
  this.backoff && this.backoff.setMin(v);
  return this;
};

Manager.prototype.randomizationFactor = function (v) {
  if (!arguments.length) return this._randomizationFactor;
  this._randomizationFactor = v;
  this.backoff && this.backoff.setJitter(v);
  return this;
};

/**
 * Sets the maximum delay between reconnections.
 *
 * @param {Number} delay
 * @return {Manager} self or value
 * @api public
 */

Manager.prototype.reconnectionDelayMax = function (v) {
  if (!arguments.length) return this._reconnectionDelayMax;
  this._reconnectionDelayMax = v;
  this.backoff && this.backoff.setMax(v);
  return this;
};

/**
 * Sets the connection timeout. `false` to disable
 *
 * @return {Manager} self or value
 * @api public
 */

Manager.prototype.timeout = function (v) {
  if (!arguments.length) return this._timeout;
  this._timeout = v;
  return this;
};

/**
 * Starts trying to reconnect if reconnection is enabled and we have not
 * started reconnecting yet
 *
 * @api private
 */

Manager.prototype.maybeReconnectOnOpen = function () {
  // Only try to reconnect if it's the first time we're connecting
  if (!this.reconnecting && this._reconnection && this.backoff.attempts === 0) {
    // keeps reconnection from firing twice for the same reconnection loop
    this.reconnect();
  }
};

/**
 * Sets the current transport `socket`.
 *
 * @param {Function} optional, callback
 * @return {Manager} self
 * @api public
 */

Manager.prototype.open =
Manager.prototype.connect = function (fn, opts) {
  debug('readyState %s', this.readyState);
  if (~this.readyState.indexOf('open')) return this;

  debug('opening %s', this.uri);
  this.engine = eio(this.uri, this.opts);
  var socket = this.engine;
  var self = this;
  this.readyState = 'opening';
  this.skipReconnect = false;

  // emit `open`
  var openSub = on(socket, 'open', function () {
    self.onopen();
    fn && fn();
  });

  // emit `connect_error`
  var errorSub = on(socket, 'error', function (data) {
    debug('connect_error');
    self.cleanup();
    self.readyState = 'closed';
    self.emitAll('connect_error', data);
    if (fn) {
      var err = new Error('Connection error');
      err.data = data;
      fn(err);
    } else {
      // Only do this if there is no fn to handle the error
      self.maybeReconnectOnOpen();
    }
  });

  // emit `connect_timeout`
  if (false !== this._timeout) {
    var timeout = this._timeout;
    debug('connect attempt will timeout after %d', timeout);

    // set timer
    var timer = setTimeout(function () {
      debug('connect attempt timed out after %d', timeout);
      openSub.destroy();
      socket.close();
      socket.emit('error', 'timeout');
      self.emitAll('connect_timeout', timeout);
    }, timeout);

    this.subs.push({
      destroy: function () {
        clearTimeout(timer);
      }
    });
  }

  this.subs.push(openSub);
  this.subs.push(errorSub);

  return this;
};

/**
 * Called upon transport open.
 *
 * @api private
 */

Manager.prototype.onopen = function () {
  debug('open');

  // clear old subs
  this.cleanup();

  // mark as open
  this.readyState = 'open';
  this.emit('open');

  // add new subs
  var socket = this.engine;
  this.subs.push(on(socket, 'data', bind(this, 'ondata')));
  this.subs.push(on(socket, 'ping', bind(this, 'onping')));
  this.subs.push(on(socket, 'pong', bind(this, 'onpong')));
  this.subs.push(on(socket, 'error', bind(this, 'onerror')));
  this.subs.push(on(socket, 'close', bind(this, 'onclose')));
  this.subs.push(on(this.decoder, 'decoded', bind(this, 'ondecoded')));
};

/**
 * Called upon a ping.
 *
 * @api private
 */

Manager.prototype.onping = function () {
  this.lastPing = new Date();
  this.emitAll('ping');
};

/**
 * Called upon a packet.
 *
 * @api private
 */

Manager.prototype.onpong = function () {
  this.emitAll('pong', new Date() - this.lastPing);
};

/**
 * Called with data.
 *
 * @api private
 */

Manager.prototype.ondata = function (data) {
  this.decoder.add(data);
};

/**
 * Called when parser fully decodes a packet.
 *
 * @api private
 */

Manager.prototype.ondecoded = function (packet) {
  this.emit('packet', packet);
};

/**
 * Called upon socket error.
 *
 * @api private
 */

Manager.prototype.onerror = function (err) {
  debug('error', err);
  this.emitAll('error', err);
};

/**
 * Creates a new socket for the given `nsp`.
 *
 * @return {Socket}
 * @api public
 */

Manager.prototype.socket = function (nsp, opts) {
  var socket = this.nsps[nsp];
  if (!socket) {
    socket = new Socket(this, nsp, opts);
    this.nsps[nsp] = socket;
    var self = this;
    socket.on('connecting', onConnecting);
    socket.on('connect', function () {
      socket.id = self.generateId(nsp);
    });

    if (this.autoConnect) {
      // manually call here since connecting event is fired before listening
      onConnecting();
    }
  }

  function onConnecting () {
    if (!~indexOf(self.connecting, socket)) {
      self.connecting.push(socket);
    }
  }

  return socket;
};

/**
 * Called upon a socket close.
 *
 * @param {Socket} socket
 */

Manager.prototype.destroy = function (socket) {
  var index = indexOf(this.connecting, socket);
  if (~index) this.connecting.splice(index, 1);
  if (this.connecting.length) return;

  this.close();
};

/**
 * Writes a packet.
 *
 * @param {Object} packet
 * @api private
 */

Manager.prototype.packet = function (packet) {
  debug('writing packet %j', packet);
  var self = this;
  if (packet.query && packet.type === 0) packet.nsp += '?' + packet.query;

  if (!self.encoding) {
    // encode, then write to engine with result
    self.encoding = true;
    this.encoder.encode(packet, function (encodedPackets) {
      for (var i = 0; i < encodedPackets.length; i++) {
        self.engine.write(encodedPackets[i], packet.options);
      }
      self.encoding = false;
      self.processPacketQueue();
    });
  } else { // add packet to the queue
    self.packetBuffer.push(packet);
  }
};

/**
 * If packet buffer is non-empty, begins encoding the
 * next packet in line.
 *
 * @api private
 */

Manager.prototype.processPacketQueue = function () {
  if (this.packetBuffer.length > 0 && !this.encoding) {
    var pack = this.packetBuffer.shift();
    this.packet(pack);
  }
};

/**
 * Clean up transport subscriptions and packet buffer.
 *
 * @api private
 */

Manager.prototype.cleanup = function () {
  debug('cleanup');

  var subsLength = this.subs.length;
  for (var i = 0; i < subsLength; i++) {
    var sub = this.subs.shift();
    sub.destroy();
  }

  this.packetBuffer = [];
  this.encoding = false;
  this.lastPing = null;

  this.decoder.destroy();
};

/**
 * Close the current socket.
 *
 * @api private
 */

Manager.prototype.close =
Manager.prototype.disconnect = function () {
  debug('disconnect');
  this.skipReconnect = true;
  this.reconnecting = false;
  if ('opening' === this.readyState) {
    // `onclose` will not fire because
    // an open event never happened
    this.cleanup();
  }
  this.backoff.reset();
  this.readyState = 'closed';
  if (this.engine) this.engine.close();
};

/**
 * Called upon engine close.
 *
 * @api private
 */

Manager.prototype.onclose = function (reason) {
  debug('onclose');

  this.cleanup();
  this.backoff.reset();
  this.readyState = 'closed';
  this.emit('close', reason);

  if (this._reconnection && !this.skipReconnect) {
    this.reconnect();
  }
};

/**
 * Attempt a reconnection.
 *
 * @api private
 */

Manager.prototype.reconnect = function () {
  if (this.reconnecting || this.skipReconnect) return this;

  var self = this;

  if (this.backoff.attempts >= this._reconnectionAttempts) {
    debug('reconnect failed');
    this.backoff.reset();
    this.emitAll('reconnect_failed');
    this.reconnecting = false;
  } else {
    var delay = this.backoff.duration();
    debug('will wait %dms before reconnect attempt', delay);

    this.reconnecting = true;
    var timer = setTimeout(function () {
      if (self.skipReconnect) return;

      debug('attempting reconnect');
      self.emitAll('reconnect_attempt', self.backoff.attempts);
      self.emitAll('reconnecting', self.backoff.attempts);

      // check again for the case socket closed in above events
      if (self.skipReconnect) return;

      self.open(function (err) {
        if (err) {
          debug('reconnect attempt error');
          self.reconnecting = false;
          self.reconnect();
          self.emitAll('reconnect_error', err.data);
        } else {
          debug('reconnect success');
          self.onreconnect();
        }
      });
    }, delay);

    this.subs.push({
      destroy: function () {
        clearTimeout(timer);
      }
    });
  }
};

/**
 * Called upon successful reconnect.
 *
 * @api private
 */

Manager.prototype.onreconnect = function () {
  var attempt = this.backoff.attempts;
  this.reconnecting = false;
  this.backoff.reset();
  this.updateSocketIds();
  this.emitAll('reconnect', attempt);
};


/***/ }),
/* 24 */
/***/ (function(module, exports) {


/**
 * Module exports.
 */

module.exports = on;

/**
 * Helper for subscriptions.
 *
 * @param {Object|EventEmitter} obj with `Emitter` mixin or `EventEmitter`
 * @param {String} event name
 * @param {Function} callback
 * @api public
 */

function on (obj, ev, fn) {
  obj.on(ev, fn);
  return {
    destroy: function () {
      obj.removeListener(ev, fn);
    }
  };
}


/***/ }),
/* 25 */
/***/ (function(module, exports, __webpack_require__) {


/**
 * Module dependencies.
 */

var parser = __webpack_require__(12);
var Emitter = __webpack_require__(1);
var toArray = __webpack_require__(70);
var on = __webpack_require__(24);
var bind = __webpack_require__(16);
var debug = __webpack_require__(7)('socket.io-client:socket');
var parseqs = __webpack_require__(6);

/**
 * Module exports.
 */

module.exports = exports = Socket;

/**
 * Internal events (blacklisted).
 * These events can't be emitted by the user.
 *
 * @api private
 */

var events = {
  connect: 1,
  connect_error: 1,
  connect_timeout: 1,
  connecting: 1,
  disconnect: 1,
  error: 1,
  reconnect: 1,
  reconnect_attempt: 1,
  reconnect_failed: 1,
  reconnect_error: 1,
  reconnecting: 1,
  ping: 1,
  pong: 1
};

/**
 * Shortcut to `Emitter#emit`.
 */

var emit = Emitter.prototype.emit;

/**
 * `Socket` constructor.
 *
 * @api public
 */

function Socket (io, nsp, opts) {
  this.io = io;
  this.nsp = nsp;
  this.json = this; // compat
  this.ids = 0;
  this.acks = {};
  this.receiveBuffer = [];
  this.sendBuffer = [];
  this.connected = false;
  this.disconnected = true;
  if (opts && opts.query) {
    this.query = opts.query;
  }
  if (this.io.autoConnect) this.open();
}

/**
 * Mix in `Emitter`.
 */

Emitter(Socket.prototype);

/**
 * Subscribe to open, close and packet events
 *
 * @api private
 */

Socket.prototype.subEvents = function () {
  if (this.subs) return;

  var io = this.io;
  this.subs = [
    on(io, 'open', bind(this, 'onopen')),
    on(io, 'packet', bind(this, 'onpacket')),
    on(io, 'close', bind(this, 'onclose'))
  ];
};

/**
 * "Opens" the socket.
 *
 * @api public
 */

Socket.prototype.open =
Socket.prototype.connect = function () {
  if (this.connected) return this;

  this.subEvents();
  this.io.open(); // ensure open
  if ('open' === this.io.readyState) this.onopen();
  this.emit('connecting');
  return this;
};

/**
 * Sends a `message` event.
 *
 * @return {Socket} self
 * @api public
 */

Socket.prototype.send = function () {
  var args = toArray(arguments);
  args.unshift('message');
  this.emit.apply(this, args);
  return this;
};

/**
 * Override `emit`.
 * If the event is in `events`, it's emitted normally.
 *
 * @param {String} event name
 * @return {Socket} self
 * @api public
 */

Socket.prototype.emit = function (ev) {
  if (events.hasOwnProperty(ev)) {
    emit.apply(this, arguments);
    return this;
  }

  var args = toArray(arguments);
  var packet = { type: parser.EVENT, data: args };

  packet.options = {};
  packet.options.compress = !this.flags || false !== this.flags.compress;

  // event ack callback
  if ('function' === typeof args[args.length - 1]) {
    debug('emitting packet with ack id %d', this.ids);
    this.acks[this.ids] = args.pop();
    packet.id = this.ids++;
  }

  if (this.connected) {
    this.packet(packet);
  } else {
    this.sendBuffer.push(packet);
  }

  delete this.flags;

  return this;
};

/**
 * Sends a packet.
 *
 * @param {Object} packet
 * @api private
 */

Socket.prototype.packet = function (packet) {
  packet.nsp = this.nsp;
  this.io.packet(packet);
};

/**
 * Called upon engine `open`.
 *
 * @api private
 */

Socket.prototype.onopen = function () {
  debug('transport is open - connecting');

  // write connect packet if necessary
  if ('/' !== this.nsp) {
    if (this.query) {
      var query = typeof this.query === 'object' ? parseqs.encode(this.query) : this.query;
      debug('sending connect packet with query %s', query);
      this.packet({type: parser.CONNECT, query: query});
    } else {
      this.packet({type: parser.CONNECT});
    }
  }
};

/**
 * Called upon engine `close`.
 *
 * @param {String} reason
 * @api private
 */

Socket.prototype.onclose = function (reason) {
  debug('close (%s)', reason);
  this.connected = false;
  this.disconnected = true;
  delete this.id;
  this.emit('disconnect', reason);
};

/**
 * Called with socket packet.
 *
 * @param {Object} packet
 * @api private
 */

Socket.prototype.onpacket = function (packet) {
  if (packet.nsp !== this.nsp) return;

  switch (packet.type) {
    case parser.CONNECT:
      this.onconnect();
      break;

    case parser.EVENT:
      this.onevent(packet);
      break;

    case parser.BINARY_EVENT:
      this.onevent(packet);
      break;

    case parser.ACK:
      this.onack(packet);
      break;

    case parser.BINARY_ACK:
      this.onack(packet);
      break;

    case parser.DISCONNECT:
      this.ondisconnect();
      break;

    case parser.ERROR:
      this.emit('error', packet.data);
      break;
  }
};

/**
 * Called upon a server event.
 *
 * @param {Object} packet
 * @api private
 */

Socket.prototype.onevent = function (packet) {
  var args = packet.data || [];
  debug('emitting event %j', args);

  if (null != packet.id) {
    debug('attaching ack callback to event');
    args.push(this.ack(packet.id));
  }

  if (this.connected) {
    emit.apply(this, args);
  } else {
    this.receiveBuffer.push(args);
  }
};

/**
 * Produces an ack callback to emit with an event.
 *
 * @api private
 */

Socket.prototype.ack = function (id) {
  var self = this;
  var sent = false;
  return function () {
    // prevent double callbacks
    if (sent) return;
    sent = true;
    var args = toArray(arguments);
    debug('sending ack %j', args);

    self.packet({
      type: parser.ACK,
      id: id,
      data: args
    });
  };
};

/**
 * Called upon a server acknowlegement.
 *
 * @param {Object} packet
 * @api private
 */

Socket.prototype.onack = function (packet) {
  var ack = this.acks[packet.id];
  if ('function' === typeof ack) {
    debug('calling ack %s with %j', packet.id, packet.data);
    ack.apply(this, packet.data);
    delete this.acks[packet.id];
  } else {
    debug('bad ack %s', packet.id);
  }
};

/**
 * Called upon server connect.
 *
 * @api private
 */

Socket.prototype.onconnect = function () {
  this.connected = true;
  this.disconnected = false;
  this.emit('connect');
  this.emitBuffered();
};

/**
 * Emit buffered events (received and emitted).
 *
 * @api private
 */

Socket.prototype.emitBuffered = function () {
  var i;
  for (i = 0; i < this.receiveBuffer.length; i++) {
    emit.apply(this, this.receiveBuffer[i]);
  }
  this.receiveBuffer = [];

  for (i = 0; i < this.sendBuffer.length; i++) {
    this.packet(this.sendBuffer[i]);
  }
  this.sendBuffer = [];
};

/**
 * Called upon server disconnect.
 *
 * @api private
 */

Socket.prototype.ondisconnect = function () {
  debug('server disconnect (%s)', this.nsp);
  this.destroy();
  this.onclose('io server disconnect');
};

/**
 * Called upon forced client/server side disconnections,
 * this method ensures the manager stops tracking us and
 * that reconnections don't get triggered for this.
 *
 * @api private.
 */

Socket.prototype.destroy = function () {
  if (this.subs) {
    // clean subscriptions to avoid reconnections
    for (var i = 0; i < this.subs.length; i++) {
      this.subs[i].destroy();
    }
    this.subs = null;
  }

  this.io.destroy(this);
};

/**
 * Disconnects the socket manually.
 *
 * @return {Socket} self
 * @api public
 */

Socket.prototype.close =
Socket.prototype.disconnect = function () {
  if (this.connected) {
    debug('performing disconnect (%s)', this.nsp);
    this.packet({ type: parser.DISCONNECT });
  }

  // remove socket from pool
  this.destroy();

  if (this.connected) {
    // fire events
    this.onclose('io client disconnect');
  }
  return this;
};

/**
 * Sets the compress flag.
 *
 * @param {Boolean} if `true`, compresses the sending data
 * @return {Socket} self
 * @api public
 */

Socket.prototype.compress = function (compress) {
  this.flags = this.flags || {};
  this.flags.compress = compress;
  return this;
};


/***/ }),
/* 26 */
/***/ (function(module, exports, __webpack_require__) {

/* WEBPACK VAR INJECTION */(function(global) {
module.exports = isBuf;

/**
 * Returns true if obj is a buffer or an arraybuffer.
 *
 * @api private
 */

function isBuf(obj) {
  return (global.Buffer && global.Buffer.isBuffer(obj)) ||
         (global.ArrayBuffer && obj instanceof ArrayBuffer);
}

/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(0)))

/***/ }),
/* 27 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var alphabet = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_'.split('')
  , length = 64
  , map = {}
  , seed = 0
  , i = 0
  , prev;

/**
 * Return a string representing the specified number.
 *
 * @param {Number} num The number to convert.
 * @returns {String} The string representation of the number.
 * @api public
 */
function encode(num) {
  var encoded = '';

  do {
    encoded = alphabet[num % length] + encoded;
    num = Math.floor(num / length);
  } while (num > 0);

  return encoded;
}

/**
 * Return the integer value specified by the given string.
 *
 * @param {String} str The string to convert.
 * @returns {Number} The integer value represented by the string.
 * @api public
 */
function decode(str) {
  var decoded = 0;

  for (i = 0; i < str.length; i++) {
    decoded = decoded * length + map[str.charAt(i)];
  }

  return decoded;
}

/**
 * Yeast: A tiny growing id generator.
 *
 * @returns {String} A unique id.
 * @api public
 */
function yeast() {
  var now = encode(+new Date());

  if (now !== prev) return seed = 0, prev = now;
  return now +'.'+ encode(seed++);
}

//
// Map each character to its index.
//
for (; i < length; i++) map[alphabet[i]] = i;

//
// Expose the `yeast`, `encode` and `decode` functions.
//
yeast.encode = encode;
yeast.decode = decode;
module.exports = yeast;


/***/ }),
/* 28 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _IDrawing2 = __webpack_require__(3);

var _IDrawing3 = _interopRequireDefault(_IDrawing2);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var VOLDrawing = function (_IDrawing) {
    _inherits(VOLDrawing, _IDrawing);

    function VOLDrawing(render, type, perid, linecolor, linewidth, name, option) {
        _classCallCheck(this, VOLDrawing);

        var _this = _possibleConstructorReturn(this, (VOLDrawing.__proto__ || Object.getPrototypeOf(VOLDrawing)).call(this, render, name, option));

        _this.type = type ? type : "c";
        _this.perid = perid ? perid : 5;
        _this.linecolor = linecolor ? linecolor : "black";
        _this.linewidth = linewidth ? linewidth : 1;
        return _this;
    }

    _createClass(VOLDrawing, [{
        key: "preDraw",
        value: function preDraw(canvasCxt, render) {}
    }, {
        key: "onDraw",
        value: function onDraw(canvasCxt, render) {
            this.drawAvg(canvasCxt, render);
        }
    }, {
        key: "drawEnd",
        value: function drawEnd(canvasCxt, render) {}
    }, {
        key: "getOrder",
        value: function getOrder() {
            return 0;
        }
    }, {
        key: "drawAvg",
        value: function drawAvg(canvasCxt, render) {
            canvasCxt.beginPath();
            var option = this.entitySet.option;
            canvasCxt.strokeStyle = this.linecolor;
            canvasCxt.lineWidth = this.linewidth;
            canvasCxt.shadowBlur = option.shadowBlur;
            canvasCxt.shadowColor = option.shadowColor;
            canvasCxt.shadowOffsetX = option.shadowOffsetX;
            canvasCxt.shadowOffsetY = option.shadowOffsetY;
            var lt = false;
            for (var i = 0; i <= this.entitySet.length(); i++) {
                var entity = this.entitySet.getEntity(i);

                var ma_y = render.entitySet.getAVG(i, this.type, this.perid);
                if (lt) {
                    canvasCxt.lineTo(render.getXPixel(i + 1), render.getYPixel(ma_y));
                }
                if (!lt && ma_y != 0) {
                    canvasCxt.moveTo(render.getXPixel(i + 1), render.getYPixel(ma_y));
                    lt = true;
                }
            }
            canvasCxt.stroke();
            canvasCxt.closePath();
        }
    }]);

    return VOLDrawing;
}(_IDrawing3.default);

exports.default = VOLDrawing;

/***/ }),
/* 29 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var BaseChart = function () {
    function BaseChart(canvasid) {
        _classCallCheck(this, BaseChart);

        this.canvas = document.querySelector(canvasid);
        this.canvasCxt = canvas.getContext('2d');
        this.width = this.canvas.width;
        this.height = this.canvas.height;
        this.left = this.canvas.offsetLeft;
        this.top = this.canvas.offsetTop;
        this.renders = new Set();
        this.orientation = "row"; //row，col

        this.initTouch(this.canvas);
    }

    _createClass(BaseChart, [{
        key: "resizeCanvas",
        value: function resizeCanvas() {
            var width = window.innerWidth;
            var height = window.innerHeight;
            this.sawtooth(width, height);
        }
    }, {
        key: "sawtooth",
        value: function sawtooth(width, height) {
            this.canvas.style.width = width + "px";
            this.canvas.style.height = height + "px";
            var ratio = window.devicePixelRatio;
            if (ratio) {
                this.width = width;
                this.height = height;
                this.canvas.width = width * ratio;
                this.canvas.height = height * ratio;
                this.canvasCxt.scale(ratio, ratio);
            } else {
                this.width = width;
                this.height = height;
                this.canvas.width = width;
                this.canvas.height = height;
            }
        }
    }, {
        key: "initTouch",
        value: function initTouch(element) {
            var _this = this;

            element.addEventListener('touchstart', function (e) {
                return _this.touch(e);
            }, false);
            element.addEventListener('touchmove', function (e) {
                return _this.touch(e);
            }, false);
            element.addEventListener('touchend', function (e) {
                return _this.touch(e);
            }, false);
            element.addEventListener('touchcancel', function (e) {
                return _this.touch(e);
            }, false);
        }
    }, {
        key: "touch",
        value: function touch(e) {
            if (e && e.type == "touchmove") {
                e.preventDefault();
            }
            this.renders.forEach(function (render) {
                render.touch(e);
            });
            this.invalidate();
        }
    }, {
        key: "addRender",
        value: function addRender(render) {
            this.renders.add(render);
        }
    }, {
        key: "removeRender",
        value: function removeRender(render) {
            if (this.renders.has(render)) {
                this.renders.delete(render);
            }
        }
    }, {
        key: "clearRender",
        value: function clearRender() {
            this.renders.clear();
        }
    }, {
        key: "draw",
        value: function draw() {
            var _this2 = this;

            this.renders.forEach(function (render) {
                render.clearRender();
                _this2.canvasCxt.save();
                _this2.canvasCxt.rect(render.drawViewPort.left, render.drawViewPort.top, render.drawViewPort.right, render.drawViewPort.bottom);
                _this2.canvasCxt.clip();
                _this2.canvasCxt.save();
                _this2.canvasCxt.translate(render.drawViewPort.left, render.drawViewPort.top + render.drawViewPort.bottom);
                _this2.canvasCxt.rotate(Math.PI);
                render.drawings.forEach(function (drawing) {
                    drawing.preDraw(_this2.canvasCxt, render);
                    drawing.onDraw(_this2.canvasCxt, render);
                    drawing.drawEnd(_this2.canvasCxt, render);
                });
                _this2.canvasCxt.restore();
                _this2.canvasCxt.restore();
            });
        }
    }, {
        key: "invalidate",
        value: function invalidate() {
            this.renders.forEach(function (render) {
                render.initUnit();
            });
            this.draw();
        }
    }, {
        key: "measure",
        value: function measure() {
            var _this3 = this;

            var weights = 0;
            var totalSpace = 0;
            var lastSpace = 0;
            this.renders.forEach(function (render) {
                var weight = render.option.weight;
                if (weight != 0) {
                    weights += weight;
                } else {
                    if (_this3.orientation == 'row') {
                        if (render.viewport.bottom <= 1) {
                            render.viewport.bottom = _this3.height * render.viewport.bottom;
                        }
                        totalSpace += render.viewport.bottom;
                    } else {
                        if (render.viewport.right <= 1) {
                            render.viewport.right = _this3.height * render.viewport.right;
                        }
                        totalSpace += render.viewport.right;
                    }
                }
            });

            if (this.orientation == 'row') {
                this.height = Math.max(totalSpace, this.height);
                lastSpace = Math.abs(totalSpace - this.height);
            } else {
                this.width = Math.max(totalSpace, this.width);
                lastSpace = Math.abs(totalSpace - this.width);
            }

            this.renders.forEach(function (render) {
                var weight = render.option.weight;
                if (_this3.orientation == 'row') {
                    if (render.option.weight != 0) {
                        render.viewport.bottom = lastSpace * render.option.weight / weights;
                        render.viewport.right = _this3.width;
                    } else {
                        if (render.viewport.bottom <= 1) {
                            render.viewport.bottom = _this3.height * render.viewport.bottom;
                        }
                        if (render.viewport.right <= 1) {
                            render.viewport.right = _this3.width * render.viewport.right;
                        }
                    }
                } else {
                    if (render.option.weight != 0) {
                        render.viewport.right = lastSpace * render.option.weight / weights;
                        render.viewport.bottom = _this3.height;
                    } else {
                        if (render.viewport.right <= 1) {
                            render.viewport.right = _this3.width * render.viewport.right;
                        }
                        if (render.viewport.bottom <= 1) {
                            render.viewport.bottom = _this3.height * render.viewport.bottom;
                        }
                    }
                }
            });
        }
    }, {
        key: "layout",
        value: function layout() {
            var _this4 = this;

            var lastX = 0;
            var lastY = 0;
            this.renders.forEach(function (render, index) {
                render.viewport = { left: lastX, top: lastY, right: render.viewport.right, bottom: render.viewport.bottom };
                render.drawViewPort = { left: lastX + render.margin[0], top: lastY + render.margin[1], right: render.viewport.right - render.margin[0] - render.margin[2], bottom: render.viewport.bottom - render.margin[1] - render.margin[3] };
                if (_this4.orientation == 'row') {
                    lastY = lastY + render.viewport.bottom;
                } else {
                    lastX = lastX + render.viewport.right;
                }
            });
        }
    }, {
        key: "render",
        value: function render() {
            var _this5 = this;

            this.measure();
            this.layout();
            this.renders.forEach(function (render) {
                render.ready();
                render.init(_this5);
            });
            this.draw();
        }
    }]);

    return BaseChart;
}();

exports.default = BaseChart;

/***/ }),
/* 30 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _IDrawing2 = __webpack_require__(3);

var _IDrawing3 = _interopRequireDefault(_IDrawing2);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var CrossDrawing = function (_IDrawing) {
    _inherits(CrossDrawing, _IDrawing);

    function CrossDrawing(render, type, linecolor, linewidth, name, option) {
        _classCallCheck(this, CrossDrawing);

        var _this = _possibleConstructorReturn(this, (CrossDrawing.__proto__ || Object.getPrototypeOf(CrossDrawing)).call(this, render, name, option));

        _this.type = type ? type : "kline";
        _this.linecolor = linecolor ? linecolor : "black";
        _this.linewidth = linewidth ? linewidth : 0.5;

        _this.callback = undefined;
        return _this;
    }

    _createClass(CrossDrawing, [{
        key: "preDraw",
        value: function preDraw(canvasCxt, render) {}
    }, {
        key: "onDraw",
        value: function onDraw(canvasCxt, render) {
            if (render.entitySet.hightlightShow) {
                this.drawCross(canvasCxt, render);
            }
        }
    }, {
        key: "drawEnd",
        value: function drawEnd(canvasCxt, render) {}
    }, {
        key: "getOrder",
        value: function getOrder() {
            return 40;
        }
    }, {
        key: "setCallBack",
        value: function setCallBack(fn) {
            this.callback = fn;
        }
    }, {
        key: "drawCross",
        value: function drawCross(canvasCxt, render) {
            canvasCxt.beginPath();
            var option = this.entitySet.option;
            canvasCxt.strokeStyle = this.linecolor;
            canvasCxt.lineWidth = this.linewidth;
            canvasCxt.shadowBlur = option.shadowBlur;
            canvasCxt.shadowColor = option.shadowColor;
            canvasCxt.shadowOffsetX = option.shadowOffsetX;
            canvasCxt.shadowOffsetY = option.shadowOffsetY;

            var x = render.entitySet.hightlightX;
            if (x % render.unitX != 0) {
                x = Math.round(x / render.unitX) * render.unitX;
            }
            render.entitySet.hightlightIndex = x;
            canvasCxt.moveTo(x, 0);
            canvasCxt.lineTo(x, render.drawViewPort.bottom);
            canvasCxt.stroke();

            var y = render.drawViewPort.top + render.drawViewPort.bottom - render.entitySet.hightlightY;
            canvasCxt.moveTo(0, y + 0.5);
            canvasCxt.lineTo(-render.drawViewPort.right, y + 0.5);
            canvasCxt.stroke();

            canvasCxt.closePath();

            if (this.callback) {
                this.callback(x, y);
            }
        }
    }]);

    return CrossDrawing;
}(_IDrawing3.default);

exports.default = CrossDrawing;

/***/ }),
/* 31 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _BaseRender2 = __webpack_require__(9);

var _BaseRender3 = _interopRequireDefault(_BaseRender2);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var IndexRender = function (_BaseRender) {
    _inherits(IndexRender, _BaseRender);

    function IndexRender(name, option) {
        _classCallCheck(this, IndexRender);

        return _possibleConstructorReturn(this, (IndexRender.__proto__ || Object.getPrototypeOf(IndexRender)).call(this, name, option));
    }

    _createClass(IndexRender, [{
        key: "initUnit",
        value: function initUnit() {
            if (this.entitySet.length() <= 0) {
                return;
            }
            var h = Number.parseFloat(this.entitySet.getMaxVolEntity().v);
            var l = 0;
            var tempUnitY = this.drawViewPort.bottom / (h - l);
            this.drawViewPortMax_H = h + this.padding[1] / tempUnitY;
            this.drawViewPortMin_L = l - this.padding[3] / tempUnitY;
            this.dy = this.drawViewPortMax_H - this.drawViewPortMin_L;
            this.unitX = this.option.klineWidth + this.option.klineGap;
            this.unitY = this.dy / this.drawViewPort.bottom;
        }
    }]);

    return IndexRender;
}(_BaseRender3.default);

exports.default = IndexRender;

/***/ }),
/* 32 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _Entity2 = __webpack_require__(14);

var _Entity3 = _interopRequireDefault(_Entity2);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var KEntity = function (_Entity) {
    _inherits(KEntity, _Entity);

    function KEntity(o, h, l, c, v, n, x, y, title, desc, width, height, option) {
        _classCallCheck(this, KEntity);

        // Object.assign(KEntity.prototype, new Entity)
        var _this = _possibleConstructorReturn(this, (KEntity.__proto__ || Object.getPrototypeOf(KEntity)).call(this, x, y, title, desc, width, height, option));

        _this.o = o;
        _this.h = h;
        _this.l = l;
        _this.c = c;
        _this.v = v;
        _this.n = n;
        return _this;
    }

    return KEntity;
}(_Entity3.default);

exports.default = KEntity;

/***/ }),
/* 33 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _IDrawing2 = __webpack_require__(3);

var _IDrawing3 = _interopRequireDefault(_IDrawing2);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var KLineDrawing = function (_IDrawing) {
    _inherits(KLineDrawing, _IDrawing);

    function KLineDrawing(render, name, option) {
        _classCallCheck(this, KLineDrawing);

        return _possibleConstructorReturn(this, (KLineDrawing.__proto__ || Object.getPrototypeOf(KLineDrawing)).call(this, render, name, option
        // Object.assign(KLineDrawing.prototype, new IDrawing)
        ));
    }

    _createClass(KLineDrawing, [{
        key: "preDraw",
        value: function preDraw(canvasCxt, render) {}
    }, {
        key: "onDraw",
        value: function onDraw(canvasCxt, render) {
            this.drawMaxminValue(canvasCxt, render);
            for (var i = 0; i < this.entitySet.length(); i++) {
                var entity = this.entitySet.getEntity(i);
                this.drawShadow(canvasCxt, render, entity, i + 1 /**- this.entitySet.startIndex + render.totalDx / render.unitX**/);
                this.drawKline(canvasCxt, render, entity, i + 1 /**- this.entitySet.startIndex + render.totalDx / render.unitX**/);
            }
        }
    }, {
        key: "drawEnd",
        value: function drawEnd(canvasCxt, render) {}
    }, {
        key: "getOrder",
        value: function getOrder() {
            return 10;
        }
    }, {
        key: "drawMaxminValue",
        value: function drawMaxminValue(canvasCxt, render) {
            var maxEntity = render.entitySet.getMaxEntity();
            var minEntity = render.entitySet.getMinEntity();
            var option1 = maxEntity.option ? maxEntity.option : this.entitySet.option;
            canvasCxt.fillStyle = option1.textColor;
            canvasCxt.font = option1.font;
            canvasCxt.save();
            canvasCxt.translate(render.getXPixel(render.entitySet.getMaxIndex() + 1), render.getYPixel(maxEntity.h));
            canvasCxt.rotate(Math.PI);
            var txt = '';
            if (Math.abs(render.entitySet.getMaxIndex() + 1 - render.getX(0)) < 8) {
                canvasCxt.textAlign = "left";
                txt = "<-" + maxEntity.h;
            } else if (Math.abs(render.getX(render.drawViewPort.right) - (render.entitySet.getMaxIndex() + 1)) < 8) {
                canvasCxt.textAlign = "right";
                txt = maxEntity.h + "->";
            } else {
                canvasCxt.textAlign = "right";
                txt = maxEntity.h + "->";
            }
            canvasCxt.fillText(txt, 0, 0);
            canvasCxt.restore();

            canvasCxt.save();
            canvasCxt.translate(render.getXPixel(render.entitySet.getMinIndex() + 1), render.getYPixel(minEntity.l));
            canvasCxt.rotate(Math.PI);
            if (Math.abs(render.entitySet.getMinIndex() + 1 - render.getX(0)) < 8) {
                canvasCxt.textAlign = "left";
                txt = "<-" + minEntity.l;
            } else if (Math.abs(render.getX(render.drawViewPort.right) - (render.entitySet.getMinIndex() + 1)) < 8) {
                canvasCxt.textAlign = "right";
                txt = minEntity.l + "->";
            } else {
                canvasCxt.textAlign = "left";
                txt = "<-" + minEntity.l;
            }
            canvasCxt.fillText(txt, 0, 9);
            canvasCxt.restore();

            canvasCxt.save();

            var v1 = render.getY(0).toFixed(2);
            var v2 = render.getY(render.drawViewPort.bottom * 1 / 3).toFixed(3);
            var v3 = render.getY(render.drawViewPort.bottom * 2 / 3).toFixed(3);
            var v4 = render.getY(render.drawViewPort.bottom - 20).toFixed(3);
            canvasCxt.rotate(Math.PI);
            canvasCxt.textAlign = "left";

            canvasCxt.fillText(" " + v1, -1, 0);
            canvasCxt.fillText(" " + v2, -1, -render.drawViewPort.bottom * 1 / 3);
            canvasCxt.fillText(" " + v3, -1, -render.drawViewPort.bottom * 2 / 3);
            canvasCxt.fillText(" " + v4, -1, -(render.drawViewPort.bottom - 20));
            canvasCxt.restore();
        }
    }, {
        key: "drawShadow",
        value: function drawShadow(canvasCxt, render, entity, index) {
            var option = entity.option ? entity.option : this.entitySet.option;

            var strokeStyle = option.upLineColor;
            if (entity.o > entity.c) {
                strokeStyle = option.downLineColor;
            } else {
                strokeStyle = option.upLineColor;
            }

            canvasCxt.strokeStyle = strokeStyle;
            canvasCxt.lineWidth = option.lineWidth;
            canvasCxt.shadowBlur = option.shadowBlur;
            canvasCxt.shadowColor = option.shadowColor;
            canvasCxt.shadowOffsetX = option.shadowOffsetX;
            canvasCxt.shadowOffsetY = option.shadowOffsetY;
            canvasCxt.globalAlpha = option.globalAlpha;
            var uh = entity.c;
            var dh = entity.c;
            if (entity.o > entity.c) {
                uh = entity.o;
                dh = entity.c;
            } else {
                uh = entity.c;
                dh = entity.o;
            }
            canvasCxt.beginPath();
            var x = render.getXPixel(index);
            var dy = 0; //this.entitySet.getMinEntity().l
            canvasCxt.moveTo(x, render.getYPixel(entity.h - dy));
            canvasCxt.lineTo(x, render.getYPixel(uh - dy));

            canvasCxt.moveTo(x, render.getYPixel(entity.l - dy));
            canvasCxt.lineTo(x, render.getYPixel(dh - dy));
            canvasCxt.stroke();
            canvasCxt.closePath();
        }
    }, {
        key: "drawKline",
        value: function drawKline(canvasCxt, render, entity, index) {
            var option = entity.option ? entity.option : this.entitySet.option;

            var fillStyle = option.upColor;
            var strokeStyle = option.upLineColor;
            var isDown = entity.o > entity.c;
            if (isDown) {
                fillStyle = option.downColor;
                strokeStyle = option.downLineColor;
            } else {
                fillStyle = option.upColor;
                strokeStyle = option.upLineColor;
            }
            canvasCxt.beginPath();
            var x = render.getXPixel(index);
            var dy = 0; //this.entitySet.getMinEntity().l
            canvasCxt.lineWidth = option.strokeWidth;
            canvasCxt.shadowBlur = option.shadowBlur;
            canvasCxt.shadowColor = option.shadowColor;
            canvasCxt.shadowOffsetX = option.shadowOffsetX;
            canvasCxt.shadowOffsetY = option.shadowOffsetY;
            canvasCxt.globalAlpha = option.globalAlpha;
            if (option.solid && isDown) {
                canvasCxt.fillStyle = fillStyle;
                var pix = 0;
                if (entity.c == entity.o) {
                    pix = 1;
                }
                canvasCxt.fillRect(x + option.klineWidth / 2, render.getYPixel(entity.o - dy), -option.klineWidth, render.getYPixel(entity.c) - render.getYPixel(entity.o) + pix);
            } else {
                canvasCxt.strokeStyle = strokeStyle;
                canvasCxt.strokeRect(x + option.klineWidth / 2 - 1, render.getYPixel(entity.o - dy), -option.klineWidth + 2, render.getYPixel(entity.c) - render.getYPixel(entity.o));
            }

            canvasCxt.closePath();
        }
    }]);

    return KLineDrawing;
}(_IDrawing3.default);

exports.default = KLineDrawing;

/***/ }),
/* 34 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _BaseRender2 = __webpack_require__(9);

var _BaseRender3 = _interopRequireDefault(_BaseRender2);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var KLineRender = function (_BaseRender) {
    _inherits(KLineRender, _BaseRender);

    function KLineRender(name, option) {
        _classCallCheck(this, KLineRender);

        return _possibleConstructorReturn(this, (KLineRender.__proto__ || Object.getPrototypeOf(KLineRender)).call(this, name, option));
    }

    return KLineRender;
}(_BaseRender3.default);

exports.default = KLineRender;

/***/ }),
/* 35 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _IDrawing2 = __webpack_require__(3);

var _IDrawing3 = _interopRequireDefault(_IDrawing2);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var TimeDrawing = function (_IDrawing) {
    _inherits(TimeDrawing, _IDrawing);

    function TimeDrawing(render, linecolor, linewidth, name, option) {
        _classCallCheck(this, TimeDrawing);

        var _this = _possibleConstructorReturn(this, (TimeDrawing.__proto__ || Object.getPrototypeOf(TimeDrawing)).call(this, render, name, option));

        _this.linecolor = linecolor ? linecolor : "#508ce7";
        _this.linewidth = linewidth ? linewidth : 1;
        return _this;
    }

    _createClass(TimeDrawing, [{
        key: "preDraw",
        value: function preDraw(canvasCxt, render) {}
    }, {
        key: "onDraw",
        value: function onDraw(canvasCxt, render) {
            this.drawTimeLine(canvasCxt, render);
            this.drawTimeArea(canvasCxt, render
            // let option = this.entitySet.option
            // canvasCxt.fillStyle = "red"
            // canvasCxt.lineWidth = option.strokeWidth
            // canvasCxt.shadowBlur = option.shadowBlur
            // canvasCxt.shadowColor = option.shadowColor
            // canvasCxt.shadowOffsetX = option.shadowOffsetX
            // canvasCxt.shadowOffsetY = option.shadowOffsetY
            // canvasCxt.globalAlpha = option.globalAlpha
            // canvasCxt.fillRect(0, 0, -10, 100)
            );
        }
    }, {
        key: "drawEnd",
        value: function drawEnd(canvasCxt, render) {}
    }, {
        key: "getOrder",
        value: function getOrder() {
            return 0;
        }
    }, {
        key: "drawTimeLine",
        value: function drawTimeLine(canvasCxt, render) {
            canvasCxt.beginPath();
            var option = this.entitySet.option;
            canvasCxt.strokeStyle = this.linecolor;
            canvasCxt.lineWidth = this.linewidth;
            // canvasCxt.globalAlpha = 0.5
            canvasCxt.shadowBlur = option.shadowBlur;
            canvasCxt.shadowColor = option.shadowColor;
            canvasCxt.shadowOffsetX = option.shadowOffsetX;
            canvasCxt.shadowOffsetY = option.shadowOffsetY;
            var lt = false;
            for (var i = 0; i < this.entitySet.length(); i++) {
                var entity = this.entitySet.getEntity(i);

                var ma_y = entity.c;
                if (lt) {
                    canvasCxt.lineTo(render.getXPixel(i + 1), render.getYPixel(ma_y));
                }
                if (!lt && ma_y != 0) {
                    canvasCxt.moveTo(render.getXPixel(i), render.getYPixel(ma_y));
                    lt = true;
                }
            }
            canvasCxt.stroke();
            canvasCxt.closePath();
        }
    }, {
        key: "drawTimeArea",
        value: function drawTimeArea(canvasCxt, render) {
            canvasCxt.beginPath();
            var option = this.entitySet.option;
            var grd = canvasCxt.createLinearGradient(0, render.drawViewPortMax_H * 10, 0, 0);
            grd.addColorStop(0, this.linecolor);
            grd.addColorStop(1, option.background);
            canvasCxt.fillStyle = grd;
            canvasCxt.globalAlpha = 0.5;

            canvasCxt.shadowBlur = option.shadowBlur;
            canvasCxt.shadowColor = option.shadowColor;
            canvasCxt.shadowOffsetX = option.shadowOffsetX;
            canvasCxt.shadowOffsetY = option.shadowOffsetY;
            canvasCxt.moveTo(0, 0);
            for (var i = 0; i < this.entitySet.length(); i++) {
                var entity = this.entitySet.getEntity(i);
                var ma_y = entity.c;
                canvasCxt.lineTo(render.getXPixel(i + 1), render.getYPixel(ma_y));
            }
            canvasCxt.lineTo(render.getXPixel(this.entitySet.length()), 0);
            canvasCxt.lineTo(0, 0);
            canvasCxt.closePath();
            canvasCxt.fill();
        }
    }]);

    return TimeDrawing;
}(_IDrawing3.default);

exports.default = TimeDrawing;

/***/ }),
/* 36 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _Entity2 = __webpack_require__(14);

var _Entity3 = _interopRequireDefault(_Entity2);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var TimeEntity = function (_Entity) {
    _inherits(TimeEntity, _Entity);

    function TimeEntity(MINT, NOW, VOLM, AMNT, AVG, x, y, title, desc, width, height, option) {
        _classCallCheck(this, TimeEntity);

        var _this = _possibleConstructorReturn(this, (TimeEntity.__proto__ || Object.getPrototypeOf(TimeEntity)).call(this, x, y, title, desc, width, height, option));

        _this.o = NOW;
        _this.h = NOW;
        _this.l = NOW;
        _this.c = NOW;
        _this.MINT = MINT;
        _this.v = VOLM;
        _this.AMNT = AMNT;
        _this.AVG = AVG;
        return _this;
    }

    return TimeEntity;
}(_Entity3.default);

exports.default = TimeEntity;

/***/ }),
/* 37 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _TimeRender2 = __webpack_require__(13);

var _TimeRender3 = _interopRequireDefault(_TimeRender2);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var TimeIndexRender = function (_TimeRender) {
    _inherits(TimeIndexRender, _TimeRender);

    function TimeIndexRender(period, name, option) {
        _classCallCheck(this, TimeIndexRender);

        return _possibleConstructorReturn(this, (TimeIndexRender.__proto__ || Object.getPrototypeOf(TimeIndexRender)).call(this, name, option));
    }

    _createClass(TimeIndexRender, [{
        key: "initUnit",
        value: function initUnit() {
            if (this.entitySet.length() <= 0) {
                return;
            }
            var h = Number.parseFloat(this.entitySet.getMaxVolEntity().v);
            var l = 0;
            var tempUnitY = this.drawViewPort.bottom / (h - l);
            this.drawViewPortMax_H = h + this.padding[1] / tempUnitY;
            this.drawViewPortMin_L = l - this.padding[3] / tempUnitY;
            this.dy = this.drawViewPortMax_H - this.drawViewPortMin_L;
            this.unitX = this.drawViewPort.right / 240; //pix/min
            this.unitY = this.dy / this.drawViewPort.bottom;

            this.entitySet.option.klineWidth = this.unitX - 2;
            this.entitySet.option.klineGap = 2;
        }
    }]);

    return TimeIndexRender;
}(_TimeRender3.default);

exports.default = TimeIndexRender;

/***/ }),
/* 38 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _IDrawing2 = __webpack_require__(3);

var _IDrawing3 = _interopRequireDefault(_IDrawing2);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var VOLDrawing = function (_IDrawing) {
    _inherits(VOLDrawing, _IDrawing);

    function VOLDrawing(render, type, name, option) {
        _classCallCheck(this, VOLDrawing);

        var _this = _possibleConstructorReturn(this, (VOLDrawing.__proto__ || Object.getPrototypeOf(VOLDrawing)).call(this, render, name, option));

        _this.type = type ? type : "kline";
        // Object.assign(KLineDrawing.prototype, new IDrawing)
        return _this;
    }

    _createClass(VOLDrawing, [{
        key: "preDraw",
        value: function preDraw(canvasCxt, render) {}
    }, {
        key: "onDraw",
        value: function onDraw(canvasCxt, render) {
            this.drawVols(canvasCxt, render);
        }
    }, {
        key: "drawEnd",
        value: function drawEnd(canvasCxt, render) {}
    }, {
        key: "getOrder",
        value: function getOrder() {
            return 10;
        }
    }, {
        key: "drawVols",
        value: function drawVols(canvasCxt, render) {
            var lastentity = undefined;
            for (var i = 0; i < this.entitySet.length(); i++) {
                var entity = this.entitySet.getEntity(i);
                var option = this.entitySet.option;

                var fillStyle = option.upColor;
                var strokeStyle = option.upLineColor;
                if (this.type == "kline") {
                    if (entity.o > entity.c) {
                        fillStyle = option.downColor;
                        strokeStyle = option.downLineColor;
                    } else {
                        fillStyle = option.upColor;
                        strokeStyle = option.upLineColor;
                    }
                } else {
                    if (!lastentity) {
                        fillStyle = "gray";
                        strokeStyle = "gray";
                    } else if (Number.parseFloat(entity.o) >= Number.parseFloat(lastentity.o)) {
                        fillStyle = option.upColor;
                        strokeStyle = option.upLineColor;
                    } else {
                        fillStyle = option.downColor;
                        strokeStyle = option.downLineColor;
                    }
                }

                canvasCxt.beginPath();
                var x = render.getXPixel(i + 1);
                canvasCxt.fillStyle = fillStyle;
                canvasCxt.shadowBlur = option.shadowBlur;
                canvasCxt.shadowColor = option.shadowColor;
                canvasCxt.shadowOffsetX = option.shadowOffsetX;
                canvasCxt.shadowOffsetY = option.shadowOffsetY;
                canvasCxt.globalAlpha = option.globalAlpha;
                canvasCxt.fillRect(x + option.klineWidth / 2, render.getYPixel(0), -option.klineWidth, render.getYPixel(entity.v));
                canvasCxt.closePath();

                lastentity = entity;
            }
        }
    }]);

    return VOLDrawing;
}(_IDrawing3.default);

exports.default = VOLDrawing;

/***/ }),
/* 39 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
var json = { "ERMS": "0", "ERMT": "执行成功", "NUM": "100", "LIST": [{ "DATE": "20170106", "TIME": "0", "OPEN": "9.170000", "HIGH": "9.170000", "LOW": "9.110000", "CLOS": "9.130000", "AMNT": "327176448.000000", "VOLM": "358154", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170109", "TIME": "0", "OPEN": "9.130000", "HIGH": "9.170000", "LOW": "9.110000", "CLOS": "9.150000", "AMNT": "329994592.000000", "VOLM": "361081", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170110", "TIME": "0", "OPEN": "9.150000", "HIGH": "9.160000", "LOW": "9.140000", "CLOS": "9.150000", "AMNT": "220575136.000000", "VOLM": "241053", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170111", "TIME": "0", "OPEN": "9.140000", "HIGH": "9.170000", "LOW": "9.130000", "CLOS": "9.140000", "AMNT": "277553216.000000", "VOLM": "303430", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170112", "TIME": "0", "OPEN": "9.130000", "HIGH": "9.170000", "LOW": "9.130000", "CLOS": "9.150000", "AMNT": "391869408.000000", "VOLM": "428006", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170113", "TIME": "0", "OPEN": "9.140000", "HIGH": "9.190000", "LOW": "9.120000", "CLOS": "9.160000", "AMNT": "397601920.000000", "VOLM": "434301", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170116", "TIME": "0", "OPEN": "9.150000", "HIGH": "9.160000", "LOW": "9.070000", "CLOS": "9.140000", "AMNT": "623025792.000000", "VOLM": "683165", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170117", "TIME": "0", "OPEN": "9.120000", "HIGH": "9.160000", "LOW": "9.100000", "CLOS": "9.150000", "AMNT": "498179712.000000", "VOLM": "545552", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170118", "TIME": "0", "OPEN": "9.140000", "HIGH": "9.190000", "LOW": "9.130000", "CLOS": "9.170000", "AMNT": "526426592.000000", "VOLM": "574269", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170119", "TIME": "0", "OPEN": "9.150000", "HIGH": "9.240000", "LOW": "9.150000", "CLOS": "9.180000", "AMNT": "402445632.000000", "VOLM": "437712", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170120", "TIME": "0", "OPEN": "9.170000", "HIGH": "9.230000", "LOW": "9.170000", "CLOS": "9.220000", "AMNT": "361865152.000000", "VOLM": "393328", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170123", "TIME": "0", "OPEN": "9.220000", "HIGH": "9.260000", "LOW": "9.200000", "CLOS": "9.220000", "AMNT": "388019072.000000", "VOLM": "420299", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170124", "TIME": "0", "OPEN": "9.230000", "HIGH": "9.280000", "LOW": "9.200000", "CLOS": "9.270000", "AMNT": "434787744.000000", "VOLM": "470244", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170125", "TIME": "0", "OPEN": "9.270000", "HIGH": "9.280000", "LOW": "9.250000", "CLOS": "9.260000", "AMNT": "281976288.000000", "VOLM": "304401", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170126", "TIME": "0", "OPEN": "9.270000", "HIGH": "9.340000", "LOW": "9.260000", "CLOS": "9.330000", "AMNT": "391844288.000000", "VOLM": "420712", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170203", "TIME": "0", "OPEN": "9.340000", "HIGH": "9.360000", "LOW": "9.230000", "CLOS": "9.260000", "AMNT": "292617952.000000", "VOLM": "315472", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170206", "TIME": "0", "OPEN": "9.260000", "HIGH": "9.320000", "LOW": "9.260000", "CLOS": "9.310000", "AMNT": "480441344.000000", "VOLM": "516786", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170207", "TIME": "0", "OPEN": "9.310000", "HIGH": "9.320000", "LOW": "9.270000", "CLOS": "9.300000", "AMNT": "368755264.000000", "VOLM": "396884", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170208", "TIME": "0", "OPEN": "9.290000", "HIGH": "9.300000", "LOW": "9.240000", "CLOS": "9.300000", "AMNT": "333975392.000000", "VOLM": "360272", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170209", "TIME": "0", "OPEN": "9.300000", "HIGH": "9.330000", "LOW": "9.280000", "CLOS": "9.310000", "AMNT": "319201024.000000", "VOLM": "342855", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170210", "TIME": "0", "OPEN": "9.320000", "HIGH": "9.360000", "LOW": "9.310000", "CLOS": "9.330000", "AMNT": "450701344.000000", "VOLM": "482743", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170213", "TIME": "0", "OPEN": "9.340000", "HIGH": "9.440000", "LOW": "9.330000", "CLOS": "9.410000", "AMNT": "599951040.000000", "VOLM": "638364", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170214", "TIME": "0", "OPEN": "9.410000", "HIGH": "9.420000", "LOW": "9.370000", "CLOS": "9.400000", "AMNT": "340318432.000000", "VOLM": "362404", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170215", "TIME": "0", "OPEN": "9.400000", "HIGH": "9.540000", "LOW": "9.390000", "CLOS": "9.450000", "AMNT": "716759680.000000", "VOLM": "756613", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170216", "TIME": "0", "OPEN": "9.450000", "HIGH": "9.500000", "LOW": "9.420000", "CLOS": "9.460000", "AMNT": "388636224.000000", "VOLM": "411161", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170217", "TIME": "0", "OPEN": "9.460000", "HIGH": "9.490000", "LOW": "9.370000", "CLOS": "9.390000", "AMNT": "400161792.000000", "VOLM": "423774", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170220", "TIME": "0", "OPEN": "9.400000", "HIGH": "9.580000", "LOW": "9.400000", "CLOS": "9.560000", "AMNT": "855710784.000000", "VOLM": "898755", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170221", "TIME": "0", "OPEN": "9.550000", "HIGH": "9.620000", "LOW": "9.540000", "CLOS": "9.570000", "AMNT": "618761088.000000", "VOLM": "646584", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170222", "TIME": "0", "OPEN": "9.570000", "HIGH": "9.570000", "LOW": "9.500000", "CLOS": "9.570000", "AMNT": "441528352.000000", "VOLM": "462966", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170223", "TIME": "0", "OPEN": "9.550000", "HIGH": "9.570000", "LOW": "9.480000", "CLOS": "9.510000", "AMNT": "319293824.000000", "VOLM": "335327", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170224", "TIME": "0", "OPEN": "9.500000", "HIGH": "9.540000", "LOW": "9.480000", "CLOS": "9.500000", "AMNT": "316020800.000000", "VOLM": "332500", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170227", "TIME": "0", "OPEN": "9.500000", "HIGH": "9.500000", "LOW": "9.420000", "CLOS": "9.430000", "AMNT": "384781024.000000", "VOLM": "407341", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170228", "TIME": "0", "OPEN": "9.430000", "HIGH": "9.510000", "LOW": "9.420000", "CLOS": "9.480000", "AMNT": "350366880.000000", "VOLM": "369719", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170301", "TIME": "0", "OPEN": "9.490000", "HIGH": "9.550000", "LOW": "9.470000", "CLOS": "9.490000", "AMNT": "330157952.000000", "VOLM": "346993", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170302", "TIME": "0", "OPEN": "9.510000", "HIGH": "9.540000", "LOW": "9.420000", "CLOS": "9.430000", "AMNT": "382395872.000000", "VOLM": "403628", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170303", "TIME": "0", "OPEN": "9.410000", "HIGH": "9.430000", "LOW": "9.360000", "CLOS": "9.400000", "AMNT": "321952544.000000", "VOLM": "342655", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170306", "TIME": "0", "OPEN": "9.400000", "HIGH": "9.460000", "LOW": "9.390000", "CLOS": "9.450000", "AMNT": "381212288.000000", "VOLM": "404511", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170307", "TIME": "0", "OPEN": "9.440000", "HIGH": "9.460000", "LOW": "9.400000", "CLOS": "9.450000", "AMNT": "277747424.000000", "VOLM": "294672", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170308", "TIME": "0", "OPEN": "9.430000", "HIGH": "9.450000", "LOW": "9.400000", "CLOS": "9.420000", "AMNT": "230425152.000000", "VOLM": "244438", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170309", "TIME": "0", "OPEN": "9.410000", "HIGH": "9.430000", "LOW": "9.360000", "CLOS": "9.380000", "AMNT": "354813696.000000", "VOLM": "378169", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170310", "TIME": "0", "OPEN": "9.380000", "HIGH": "9.410000", "LOW": "9.360000", "CLOS": "9.400000", "AMNT": "366140064.000000", "VOLM": "390182", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170313", "TIME": "0", "OPEN": "9.390000", "HIGH": "9.450000", "LOW": "9.370000", "CLOS": "9.440000", "AMNT": "513148480.000000", "VOLM": "545304", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170314", "TIME": "0", "OPEN": "9.430000", "HIGH": "9.460000", "LOW": "9.410000", "CLOS": "9.440000", "AMNT": "381594080.000000", "VOLM": "404484", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170315", "TIME": "0", "OPEN": "9.420000", "HIGH": "9.480000", "LOW": "9.420000", "CLOS": "9.480000", "AMNT": "515943744.000000", "VOLM": "546560", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170316", "TIME": "0", "OPEN": "9.480000", "HIGH": "9.530000", "LOW": "9.450000", "CLOS": "9.520000", "AMNT": "603450048.000000", "VOLM": "635953", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170317", "TIME": "0", "OPEN": "9.450000", "HIGH": "9.460000", "LOW": "9.300000", "CLOS": "9.310000", "AMNT": "1482737024.000000", "VOLM": "1583647", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170320", "TIME": "0", "OPEN": "9.290000", "HIGH": "9.310000", "LOW": "9.220000", "CLOS": "9.250000", "AMNT": "661887040.000000", "VOLM": "715021", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170321", "TIME": "0", "OPEN": "9.250000", "HIGH": "9.260000", "LOW": "9.200000", "CLOS": "9.240000", "AMNT": "511914336.000000", "VOLM": "554648", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170322", "TIME": "0", "OPEN": "9.200000", "HIGH": "9.220000", "LOW": "9.140000", "CLOS": "9.160000", "AMNT": "519331040.000000", "VOLM": "566123", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170323", "TIME": "0", "OPEN": "9.160000", "HIGH": "9.240000", "LOW": "9.150000", "CLOS": "9.200000", "AMNT": "398986560.000000", "VOLM": "433588", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170324", "TIME": "0", "OPEN": "9.200000", "HIGH": "9.240000", "LOW": "9.160000", "CLOS": "9.190000", "AMNT": "653533824.000000", "VOLM": "710827", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170327", "TIME": "0", "OPEN": "9.120000", "HIGH": "9.190000", "LOW": "9.080000", "CLOS": "9.140000", "AMNT": "900094464.000000", "VOLM": "985012", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170328", "TIME": "0", "OPEN": "9.160000", "HIGH": "9.170000", "LOW": "9.100000", "CLOS": "9.120000", "AMNT": "439405824.000000", "VOLM": "481372", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170329", "TIME": "0", "OPEN": "9.130000", "HIGH": "9.150000", "LOW": "9.090000", "CLOS": "9.110000", "AMNT": "548060672.000000", "VOLM": "601140", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170330", "TIME": "0", "OPEN": "9.120000", "HIGH": "9.120000", "LOW": "9.060000", "CLOS": "9.080000", "AMNT": "623995520.000000", "VOLM": "687285", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170331", "TIME": "0", "OPEN": "9.080000", "HIGH": "9.180000", "LOW": "9.080000", "CLOS": "9.170000", "AMNT": "578579008.000000", "VOLM": "633121", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170405", "TIME": "0", "OPEN": "9.160000", "HIGH": "9.220000", "LOW": "9.150000", "CLOS": "9.210000", "AMNT": "458567840.000000", "VOLM": "499150", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170406", "TIME": "0", "OPEN": "9.200000", "HIGH": "9.220000", "LOW": "9.170000", "CLOS": "9.200000", "AMNT": "399118976.000000", "VOLM": "434391", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170407", "TIME": "0", "OPEN": "9.190000", "HIGH": "9.220000", "LOW": "9.170000", "CLOS": "9.200000", "AMNT": "473366400.000000", "VOLM": "514844", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170410", "TIME": "0", "OPEN": "9.200000", "HIGH": "9.210000", "LOW": "9.170000", "CLOS": "9.180000", "AMNT": "368682016.000000", "VOLM": "401343", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170411", "TIME": "0", "OPEN": "9.170000", "HIGH": "9.190000", "LOW": "9.090000", "CLOS": "9.150000", "AMNT": "560304064.000000", "VOLM": "612437", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170412", "TIME": "0", "OPEN": "9.160000", "HIGH": "9.170000", "LOW": "9.100000", "CLOS": "9.120000", "AMNT": "416101248.000000", "VOLM": "455336", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170413", "TIME": "0", "OPEN": "9.110000", "HIGH": "9.140000", "LOW": "9.100000", "CLOS": "9.120000", "AMNT": "325949856.000000", "VOLM": "357442", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170414", "TIME": "0", "OPEN": "9.110000", "HIGH": "9.120000", "LOW": "9.060000", "CLOS": "9.080000", "AMNT": "445637472.000000", "VOLM": "490500", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170417", "TIME": "0", "OPEN": "9.080000", "HIGH": "9.110000", "LOW": "9.050000", "CLOS": "9.100000", "AMNT": "483130496.000000", "VOLM": "531892", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170418", "TIME": "0", "OPEN": "9.090000", "HIGH": "9.100000", "LOW": "9.050000", "CLOS": "9.050000", "AMNT": "304162848.000000", "VOLM": "335376", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170419", "TIME": "0", "OPEN": "9.030000", "HIGH": "9.040000", "LOW": "8.900000", "CLOS": "8.910000", "AMNT": "714963648.000000", "VOLM": "799668", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170420", "TIME": "0", "OPEN": "8.900000", "HIGH": "8.940000", "LOW": "8.890000", "CLOS": "8.920000", "AMNT": "390063552.000000", "VOLM": "437629", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170421", "TIME": "0", "OPEN": "8.920000", "HIGH": "8.990000", "LOW": "8.900000", "CLOS": "8.970000", "AMNT": "291126080.000000", "VOLM": "325408", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170424", "TIME": "0", "OPEN": "8.970000", "HIGH": "8.980000", "LOW": "8.890000", "CLOS": "8.930000", "AMNT": "352926400.000000", "VOLM": "394995", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170425", "TIME": "0", "OPEN": "8.930000", "HIGH": "9.010000", "LOW": "8.930000", "CLOS": "9.000000", "AMNT": "339230144.000000", "VOLM": "377933", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170426", "TIME": "0", "OPEN": "9.000000", "HIGH": "9.010000", "LOW": "8.960000", "CLOS": "8.990000", "AMNT": "343316096.000000", "VOLM": "382147", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170427", "TIME": "0", "OPEN": "8.970000", "HIGH": "8.980000", "LOW": "8.910000", "CLOS": "8.970000", "AMNT": "346186976.000000", "VOLM": "387392", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170428", "TIME": "0", "OPEN": "8.960000", "HIGH": "8.990000", "LOW": "8.920000", "CLOS": "8.990000", "AMNT": "256106704.000000", "VOLM": "286446", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170502", "TIME": "0", "OPEN": "8.930000", "HIGH": "8.960000", "LOW": "8.910000", "CLOS": "8.940000", "AMNT": "277665888.000000", "VOLM": "311026", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170503", "TIME": "0", "OPEN": "8.920000", "HIGH": "8.930000", "LOW": "8.890000", "CLOS": "8.910000", "AMNT": "249660880.000000", "VOLM": "280310", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170504", "TIME": "0", "OPEN": "8.890000", "HIGH": "8.890000", "LOW": "8.720000", "CLOS": "8.740000", "AMNT": "613783424.000000", "VOLM": "696517", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170505", "TIME": "0", "OPEN": "8.740000", "HIGH": "8.760000", "LOW": "8.580000", "CLOS": "8.630000", "AMNT": "539541760.000000", "VOLM": "623700", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170509", "TIME": "0", "OPEN": "8.560000", "HIGH": "8.640000", "LOW": "8.550000", "CLOS": "8.640000", "AMNT": "279119680.000000", "VOLM": "324194", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170510", "TIME": "0", "OPEN": "8.630000", "HIGH": "8.800000", "LOW": "8.620000", "CLOS": "8.670000", "AMNT": "498675296.000000", "VOLM": "573077", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170511", "TIME": "0", "OPEN": "8.650000", "HIGH": "8.720000", "LOW": "8.600000", "CLOS": "8.700000", "AMNT": "436444288.000000", "VOLM": "503643", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170512", "TIME": "0", "OPEN": "8.680000", "HIGH": "8.900000", "LOW": "8.640000", "CLOS": "8.900000", "AMNT": "809131904.000000", "VOLM": "917968", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170515", "TIME": "0", "OPEN": "8.890000", "HIGH": "8.950000", "LOW": "8.810000", "CLOS": "8.860000", "AMNT": "476417664.000000", "VOLM": "536578", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170516", "TIME": "0", "OPEN": "8.840000", "HIGH": "8.850000", "LOW": "8.730000", "CLOS": "8.840000", "AMNT": "461069312.000000", "VOLM": "524872", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170517", "TIME": "0", "OPEN": "8.810000", "HIGH": "8.810000", "LOW": "8.750000", "CLOS": "8.770000", "AMNT": "365987552.000000", "VOLM": "417338", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170518", "TIME": "0", "OPEN": "8.720000", "HIGH": "8.770000", "LOW": "8.710000", "CLOS": "8.730000", "AMNT": "198765584.000000", "VOLM": "227401", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170519", "TIME": "0", "OPEN": "8.740000", "HIGH": "8.760000", "LOW": "8.680000", "CLOS": "8.690000", "AMNT": "256396864.000000", "VOLM": "294270", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170522", "TIME": "0", "OPEN": "8.680000", "HIGH": "8.730000", "LOW": "8.610000", "CLOS": "8.680000", "AMNT": "588546752.000000", "VOLM": "679120", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170523", "TIME": "0", "OPEN": "8.670000", "HIGH": "8.840000", "LOW": "8.640000", "CLOS": "8.790000", "AMNT": "702123776.000000", "VOLM": "800040", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170524", "TIME": "0", "OPEN": "8.780000", "HIGH": "8.830000", "LOW": "8.680000", "CLOS": "8.810000", "AMNT": "466560640.000000", "VOLM": "532376", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170525", "TIME": "0", "OPEN": "8.790000", "HIGH": "9.140000", "LOW": "8.780000", "CLOS": "9.100000", "AMNT": "1462763008.000000", "VOLM": "1621543", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170526", "TIME": "0", "OPEN": "9.080000", "HIGH": "9.130000", "LOW": "9.040000", "CLOS": "9.100000", "AMNT": "813387200.000000", "VOLM": "895365", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170530", "TIME": "0", "OPEN": "9.110000", "HIGH": "9.150000", "LOW": "9.110000", "CLOS": "9.150000", "AMNT": "20082.000000", "VOLM": "22", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170531", "TIME": "0", "OPEN": "9.180000", "HIGH": "9.230000", "LOW": "9.110000", "CLOS": "9.200000", "AMNT": "948741888.000000", "VOLM": "1033210", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170601", "TIME": "0", "OPEN": "9.200000", "HIGH": "9.230000", "LOW": "9.120000", "CLOS": "9.190000", "AMNT": "519544672.000000", "VOLM": "566182", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170602", "TIME": "0", "OPEN": "9.180000", "HIGH": "9.290000", "LOW": "9.140000", "CLOS": "9.170000", "AMNT": "710119488.000000", "VOLM": "770757", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170605", "TIME": "0", "OPEN": "9.130000", "HIGH": "9.170000", "LOW": "8.990000", "CLOS": "9.030000", "AMNT": "573460416.000000", "VOLM": "634132", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170606", "TIME": "0", "OPEN": "9.010000", "HIGH": "9.060000", "LOW": "8.990000", "CLOS": "9.040000", "AMNT": "320520896.000000", "VOLM": "355341", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170607", "TIME": "0", "OPEN": "9.020000", "HIGH": "9.150000", "LOW": "9.010000", "CLOS": "9.130000", "AMNT": "587357696.000000", "VOLM": "645723", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }, { "DATE": "20170608", "TIME": "0", "OPEN": "9.110000", "HIGH": "9.150000", "LOW": "9.080000", "CLOS": "9.130000", "AMNT": "348860768.000000", "VOLM": "383004", "UP": "0", "DOWN": "0", "ZJS": "0.000000" }] };

exports.default = json;

/***/ }),
/* 40 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
var times = { "ERMS": "0", "ERMT": "执行成功", "NUM": "240", "LIST": [{ "MINT": "571", "NOW": "9.100000", "VOLM": "7632", "AMNT": "6950566.000000", "AVG": "9.107000" }, { "MINT": "572", "NOW": "9.100000", "VOLM": "1142", "AMNT": "1039873.000000", "AVG": "9.106000" }, { "MINT": "573", "NOW": "9.110000", "VOLM": "1435", "AMNT": "1306574.000000", "AVG": "9.106000" }, { "MINT": "574", "NOW": "9.100000", "VOLM": "1340", "AMNT": "1220882.000000", "AVG": "9.107000" }, { "MINT": "575", "NOW": "9.110000", "VOLM": "1709", "AMNT": "1556445.000000", "AVG": "9.107000" }, { "MINT": "576", "NOW": "9.110000", "VOLM": "3211", "AMNT": "2922244.000000", "AVG": "9.105000" }, { "MINT": "577", "NOW": "9.110000", "VOLM": "3105", "AMNT": "2826470.000000", "AVG": "9.105000" }, { "MINT": "578", "NOW": "9.110000", "VOLM": "1935", "AMNT": "1761836.000000", "AVG": "9.105000" }, { "MINT": "579", "NOW": "9.110000", "VOLM": "5164", "AMNT": "4700544.000000", "AVG": "9.104000" }, { "MINT": "580", "NOW": "9.100000", "VOLM": "2800", "AMNT": "2548114.000000", "AVG": "9.104000" }, { "MINT": "581", "NOW": "9.100000", "VOLM": "2585", "AMNT": "2353298.000000", "AVG": "9.104000" }, { "MINT": "582", "NOW": "9.100000", "VOLM": "2439", "AMNT": "2219810.000000", "AVG": "9.104000" }, { "MINT": "583", "NOW": "9.110000", "VOLM": "1817", "AMNT": "1654376.000000", "AVG": "9.104000" }, { "MINT": "584", "NOW": "9.100000", "VOLM": "857", "AMNT": "780348.000000", "AVG": "9.104000" }, { "MINT": "585", "NOW": "9.110000", "VOLM": "1778", "AMNT": "1619060.000000", "AVG": "9.104000" }, { "MINT": "586", "NOW": "9.100000", "VOLM": "5945", "AMNT": "5410404.000000", "AVG": "9.103000" }, { "MINT": "587", "NOW": "9.110000", "VOLM": "742", "AMNT": "675500.000000", "AVG": "9.103000" }, { "MINT": "588", "NOW": "9.100000", "VOLM": "2727", "AMNT": "2481724.000000", "AVG": "9.103000" }, { "MINT": "589", "NOW": "9.090000", "VOLM": "2479", "AMNT": "2255920.000000", "AVG": "9.103000" }, { "MINT": "590", "NOW": "9.090000", "VOLM": "1645", "AMNT": "1496920.000000", "AVG": "9.103000" }, { "MINT": "591", "NOW": "9.090000", "VOLM": "2243", "AMNT": "2040104.000000", "AVG": "9.103000" }, { "MINT": "592", "NOW": "9.090000", "VOLM": "1911", "AMNT": "1737840.000000", "AVG": "9.102000" }, { "MINT": "593", "NOW": "9.100000", "VOLM": "1227", "AMNT": "1115680.000000", "AVG": "9.102000" }, { "MINT": "594", "NOW": "9.090000", "VOLM": "6455", "AMNT": "5867932.000000", "AVG": "9.101000" }, { "MINT": "595", "NOW": "9.100000", "VOLM": "1908", "AMNT": "1735328.000000", "AVG": "9.101000" }, { "MINT": "596", "NOW": "9.090000", "VOLM": "1139", "AMNT": "1035564.000000", "AVG": "9.100000" }, { "MINT": "597", "NOW": "9.090000", "VOLM": "912", "AMNT": "829280.000000", "AVG": "9.100000" }, { "MINT": "598", "NOW": "9.090000", "VOLM": "1058", "AMNT": "962188.000000", "AVG": "9.100000" }, { "MINT": "599", "NOW": "9.080000", "VOLM": "3214", "AMNT": "2920788.000000", "AVG": "9.100000" }, { "MINT": "600", "NOW": "9.090000", "VOLM": "1042", "AMNT": "947108.000000", "AVG": "9.100000" }, { "MINT": "601", "NOW": "9.090000", "VOLM": "1661", "AMNT": "1508656.000000", "AVG": "9.099000" }, { "MINT": "602", "NOW": "9.080000", "VOLM": "1083", "AMNT": "983680.000000", "AVG": "9.099000" }, { "MINT": "603", "NOW": "9.090000", "VOLM": "2518", "AMNT": "2288136.000000", "AVG": "9.099000" }, { "MINT": "604", "NOW": "9.090000", "VOLM": "2111", "AMNT": "1918680.000000", "AVG": "9.098000" }, { "MINT": "605", "NOW": "9.090000", "VOLM": "508", "AMNT": "461872.000000", "AVG": "9.098000" }, { "MINT": "606", "NOW": "9.100000", "VOLM": "634", "AMNT": "576504.000000", "AVG": "9.098000" }, { "MINT": "607", "NOW": "9.100000", "VOLM": "1084", "AMNT": "985680.000000", "AVG": "9.098000" }, { "MINT": "608", "NOW": "9.100000", "VOLM": "603", "AMNT": "548344.000000", "AVG": "9.098000" }, { "MINT": "609", "NOW": "9.100000", "VOLM": "207", "AMNT": "188312.000000", "AVG": "9.098000" }, { "MINT": "610", "NOW": "9.100000", "VOLM": "604", "AMNT": "549328.000000", "AVG": "9.098000" }, { "MINT": "611", "NOW": "9.090000", "VOLM": "878", "AMNT": "798584.000000", "AVG": "9.098000" }, { "MINT": "612", "NOW": "9.090000", "VOLM": "678", "AMNT": "616832.000000", "AVG": "9.098000" }, { "MINT": "613", "NOW": "9.090000", "VOLM": "1436", "AMNT": "1305464.000000", "AVG": "9.098000" }, { "MINT": "614", "NOW": "9.090000", "VOLM": "735", "AMNT": "668416.000000", "AVG": "9.098000" }, { "MINT": "615", "NOW": "9.090000", "VOLM": "393", "AMNT": "358184.000000", "AVG": "9.098000" }, { "MINT": "616", "NOW": "9.090000", "VOLM": "1091", "AMNT": "991712.000000", "AVG": "9.098000" }, { "MINT": "617", "NOW": "9.100000", "VOLM": "1118", "AMNT": "1016496.000000", "AVG": "9.098000" }, { "MINT": "618", "NOW": "9.100000", "VOLM": "433", "AMNT": "393736.000000", "AVG": "9.098000" }, { "MINT": "619", "NOW": "9.090000", "VOLM": "1425", "AMNT": "1296064.000000", "AVG": "9.098000" }, { "MINT": "620", "NOW": "9.100000", "VOLM": "497", "AMNT": "451968.000000", "AVG": "9.098000" }, { "MINT": "621", "NOW": "9.100000", "VOLM": "4021", "AMNT": "3655360.000000", "AVG": "9.097000" }, { "MINT": "622", "NOW": "9.090000", "VOLM": "795", "AMNT": "723048.000000", "AVG": "9.097000" }, { "MINT": "623", "NOW": "9.090000", "VOLM": "721", "AMNT": "655664.000000", "AVG": "9.097000" }, { "MINT": "624", "NOW": "9.100000", "VOLM": "1546", "AMNT": "1406552.000000", "AVG": "9.097000" }, { "MINT": "625", "NOW": "9.110000", "VOLM": "2499", "AMNT": "2274216.000000", "AVG": "9.097000" }, { "MINT": "626", "NOW": "9.110000", "VOLM": "601", "AMNT": "547336.000000", "AVG": "9.097000" }, { "MINT": "627", "NOW": "9.100000", "VOLM": "659", "AMNT": "600056.000000", "AVG": "9.097000" }, { "MINT": "628", "NOW": "9.100000", "VOLM": "1589", "AMNT": "1446408.000000", "AVG": "9.098000" }, { "MINT": "629", "NOW": "9.110000", "VOLM": "1225", "AMNT": "1114864.000000", "AVG": "9.098000" }, { "MINT": "630", "NOW": "9.100000", "VOLM": "734", "AMNT": "668160.000000", "AVG": "9.098000" }, { "MINT": "631", "NOW": "9.100000", "VOLM": "291", "AMNT": "264840.000000", "AVG": "9.098000" }, { "MINT": "632", "NOW": "9.100000", "VOLM": "684", "AMNT": "622448.000000", "AVG": "9.098000" }, { "MINT": "633", "NOW": "9.100000", "VOLM": "840", "AMNT": "764544.000000", "AVG": "9.098000" }, { "MINT": "634", "NOW": "9.100000", "VOLM": "814", "AMNT": "740072.000000", "AVG": "9.098000" }, { "MINT": "635", "NOW": "9.100000", "VOLM": "761", "AMNT": "692536.000000", "AVG": "9.098000" }, { "MINT": "636", "NOW": "9.100000", "VOLM": "314", "AMNT": "285816.000000", "AVG": "9.098000" }, { "MINT": "637", "NOW": "9.100000", "VOLM": "167", "AMNT": "152016.000000", "AVG": "9.098000" }, { "MINT": "638", "NOW": "9.110000", "VOLM": "625", "AMNT": "568816.000000", "AVG": "9.098000" }, { "MINT": "639", "NOW": "9.100000", "VOLM": "373", "AMNT": "339440.000000", "AVG": "9.098000" }, { "MINT": "640", "NOW": "9.100000", "VOLM": "392", "AMNT": "356752.000000", "AVG": "9.098000" }, { "MINT": "641", "NOW": "9.100000", "VOLM": "206", "AMNT": "187472.000000", "AVG": "9.098000" }, { "MINT": "642", "NOW": "9.110000", "VOLM": "1383", "AMNT": "1258064.000000", "AVG": "9.098000" }, { "MINT": "643", "NOW": "9.090000", "VOLM": "759", "AMNT": "689952.000000", "AVG": "9.098000" }, { "MINT": "644", "NOW": "9.100000", "VOLM": "1254", "AMNT": "1140320.000000", "AVG": "9.098000" }, { "MINT": "645", "NOW": "9.090000", "VOLM": "368", "AMNT": "334544.000000", "AVG": "9.098000" }, { "MINT": "646", "NOW": "9.090000", "VOLM": "404", "AMNT": "367344.000000", "AVG": "9.098000" }, { "MINT": "647", "NOW": "9.090000", "VOLM": "258", "AMNT": "234288.000000", "AVG": "9.097000" }, { "MINT": "648", "NOW": "9.090000", "VOLM": "627", "AMNT": "570064.000000", "AVG": "9.097000" }, { "MINT": "649", "NOW": "9.090000", "VOLM": "820", "AMNT": "745552.000000", "AVG": "9.097000" }, { "MINT": "650", "NOW": "9.100000", "VOLM": "4801", "AMNT": "4369488.000000", "AVG": "9.098000" }, { "MINT": "651", "NOW": "9.100000", "VOLM": "308", "AMNT": "280304.000000", "AVG": "9.098000" }, { "MINT": "652", "NOW": "9.100000", "VOLM": "1131", "AMNT": "1029224.000000", "AVG": "9.098000" }, { "MINT": "653", "NOW": "9.100000", "VOLM": "593", "AMNT": "539080.000000", "AVG": "9.098000" }, { "MINT": "654", "NOW": "9.100000", "VOLM": "1522", "AMNT": "1385416.000000", "AVG": "9.098000" }, { "MINT": "655", "NOW": "9.100000", "VOLM": "1401", "AMNT": "1276016.000000", "AVG": "9.098000" }, { "MINT": "656", "NOW": "9.110000", "VOLM": "2423", "AMNT": "2207304.000000", "AVG": "9.098000" }, { "MINT": "657", "NOW": "9.110000", "VOLM": "572", "AMNT": "520848.000000", "AVG": "9.098000" }, { "MINT": "658", "NOW": "9.110000", "VOLM": "1231", "AMNT": "1121728.000000", "AVG": "9.098000" }, { "MINT": "659", "NOW": "9.120000", "VOLM": "1186", "AMNT": "1080360.000000", "AVG": "9.098000" }, { "MINT": "660", "NOW": "9.110000", "VOLM": "1593", "AMNT": "1451464.000000", "AVG": "9.098000" }, { "MINT": "661", "NOW": "9.120000", "VOLM": "4772", "AMNT": "4346064.000000", "AVG": "9.099000" }, { "MINT": "662", "NOW": "9.110000", "VOLM": "345", "AMNT": "314328.000000", "AVG": "9.099000" }, { "MINT": "663", "NOW": "9.110000", "VOLM": "2389", "AMNT": "2176400.000000", "AVG": "9.099000" }, { "MINT": "664", "NOW": "9.120000", "VOLM": "376", "AMNT": "342648.000000", "AVG": "9.099000" }, { "MINT": "665", "NOW": "9.110000", "VOLM": "2708", "AMNT": "2467176.000000", "AVG": "9.099000" }, { "MINT": "666", "NOW": "9.110000", "VOLM": "3831", "AMNT": "3489912.000000", "AVG": "9.099000" }, { "MINT": "667", "NOW": "9.110000", "VOLM": "541", "AMNT": "492928.000000", "AVG": "9.099000" }, { "MINT": "668", "NOW": "9.110000", "VOLM": "1339", "AMNT": "1219664.000000", "AVG": "9.099000" }, { "MINT": "669", "NOW": "9.110000", "VOLM": "575", "AMNT": "523472.000000", "AVG": "9.099000" }, { "MINT": "670", "NOW": "9.110000", "VOLM": "2414", "AMNT": "2198944.000000", "AVG": "9.100000" }, { "MINT": "671", "NOW": "9.120000", "VOLM": "472", "AMNT": "430000.000000", "AVG": "9.100000" }, { "MINT": "672", "NOW": "9.110000", "VOLM": "440", "AMNT": "401088.000000", "AVG": "9.100000" }, { "MINT": "673", "NOW": "9.110000", "VOLM": "2205", "AMNT": "2008592.000000", "AVG": "9.100000" }, { "MINT": "674", "NOW": "9.100000", "VOLM": "931", "AMNT": "847632.000000", "AVG": "9.100000" }, { "MINT": "675", "NOW": "9.100000", "VOLM": "19262", "AMNT": "17519296.000000", "AVG": "9.099000" }, { "MINT": "676", "NOW": "9.100000", "VOLM": "4942", "AMNT": "4497008.000000", "AVG": "9.099000" }, { "MINT": "677", "NOW": "9.100000", "VOLM": "2686", "AMNT": "2444928.000000", "AVG": "9.099000" }, { "MINT": "678", "NOW": "9.100000", "VOLM": "922", "AMNT": "838912.000000", "AVG": "9.099000" }, { "MINT": "679", "NOW": "9.110000", "VOLM": "491", "AMNT": "446976.000000", "AVG": "9.099000" }, { "MINT": "680", "NOW": "9.100000", "VOLM": "708", "AMNT": "644432.000000", "AVG": "9.099000" }, { "MINT": "681", "NOW": "9.100000", "VOLM": "1830", "AMNT": "1666752.000000", "AVG": "9.099000" }, { "MINT": "682", "NOW": "9.110000", "VOLM": "1993", "AMNT": "1815280.000000", "AVG": "9.099000" }, { "MINT": "683", "NOW": "9.110000", "VOLM": "1388", "AMNT": "1264128.000000", "AVG": "9.100000" }, { "MINT": "684", "NOW": "9.100000", "VOLM": "1757", "AMNT": "1600112.000000", "AVG": "9.100000" }, { "MINT": "685", "NOW": "9.110000", "VOLM": "2036", "AMNT": "1854400.000000", "AVG": "9.100000" }, { "MINT": "686", "NOW": "9.100000", "VOLM": "1920", "AMNT": "1748672.000000", "AVG": "9.100000" }, { "MINT": "687", "NOW": "9.110000", "VOLM": "1788", "AMNT": "1628288.000000", "AVG": "9.100000" }, { "MINT": "688", "NOW": "9.110000", "VOLM": "3274", "AMNT": "2983568.000000", "AVG": "9.100000" }, { "MINT": "689", "NOW": "9.120000", "VOLM": "1508", "AMNT": "1374944.000000", "AVG": "9.100000" }, { "MINT": "690", "NOW": "9.110000", "VOLM": "2714", "AMNT": "2471680.000000", "AVG": "9.100000" }, { "MINT": "781", "NOW": "9.110000", "VOLM": "4333", "AMNT": "3948320.000000", "AVG": "9.100000" }, { "MINT": "782", "NOW": "9.120000", "VOLM": "2897", "AMNT": "2640672.000000", "AVG": "9.101000" }, { "MINT": "783", "NOW": "9.110000", "VOLM": "3226", "AMNT": "2941536.000000", "AVG": "9.101000" }, { "MINT": "784", "NOW": "9.120000", "VOLM": "1935", "AMNT": "1764208.000000", "AVG": "9.101000" }, { "MINT": "785", "NOW": "9.110000", "VOLM": "574", "AMNT": "522976.000000", "AVG": "9.101000" }, { "MINT": "786", "NOW": "9.110000", "VOLM": "629", "AMNT": "573248.000000", "AVG": "9.101000" }, { "MINT": "787", "NOW": "9.110000", "VOLM": "685", "AMNT": "624112.000000", "AVG": "9.101000" }, { "MINT": "788", "NOW": "9.110000", "VOLM": "248", "AMNT": "225568.000000", "AVG": "9.101000" }, { "MINT": "789", "NOW": "9.120000", "VOLM": "794", "AMNT": "723344.000000", "AVG": "9.101000" }, { "MINT": "790", "NOW": "9.110000", "VOLM": "726", "AMNT": "661536.000000", "AVG": "9.101000" }, { "MINT": "791", "NOW": "9.120000", "VOLM": "703", "AMNT": "640560.000000", "AVG": "9.101000" }, { "MINT": "792", "NOW": "9.110000", "VOLM": "871", "AMNT": "793216.000000", "AVG": "9.101000" }, { "MINT": "793", "NOW": "9.120000", "VOLM": "300", "AMNT": "273872.000000", "AVG": "9.101000" }, { "MINT": "794", "NOW": "9.110000", "VOLM": "314", "AMNT": "286048.000000", "AVG": "9.101000" }, { "MINT": "795", "NOW": "9.100000", "VOLM": "471", "AMNT": "428672.000000", "AVG": "9.101000" }, { "MINT": "796", "NOW": "9.100000", "VOLM": "550", "AMNT": "500752.000000", "AVG": "9.101000" }, { "MINT": "797", "NOW": "9.100000", "VOLM": "449", "AMNT": "408608.000000", "AVG": "9.101000" }, { "MINT": "798", "NOW": "9.100000", "VOLM": "441", "AMNT": "401376.000000", "AVG": "9.101000" }, { "MINT": "799", "NOW": "9.110000", "VOLM": "522", "AMNT": "474208.000000", "AVG": "9.101000" }, { "MINT": "800", "NOW": "9.100000", "VOLM": "1400", "AMNT": "1274224.000000", "AVG": "9.101000" }, { "MINT": "801", "NOW": "9.100000", "VOLM": "1070", "AMNT": "973728.000000", "AVG": "9.101000" }, { "MINT": "802", "NOW": "9.100000", "VOLM": "825", "AMNT": "751584.000000", "AVG": "9.101000" }, { "MINT": "803", "NOW": "9.100000", "VOLM": "2038", "AMNT": "1854656.000000", "AVG": "9.101000" }, { "MINT": "804", "NOW": "9.110000", "VOLM": "510", "AMNT": "464112.000000", "AVG": "9.101000" }, { "MINT": "805", "NOW": "9.090000", "VOLM": "860", "AMNT": "782480.000000", "AVG": "9.101000" }, { "MINT": "806", "NOW": "9.090000", "VOLM": "363", "AMNT": "330080.000000", "AVG": "9.101000" }, { "MINT": "807", "NOW": "9.090000", "VOLM": "816", "AMNT": "741504.000000", "AVG": "9.101000" }, { "MINT": "808", "NOW": "9.100000", "VOLM": "336", "AMNT": "305568.000000", "AVG": "9.101000" }, { "MINT": "809", "NOW": "9.100000", "VOLM": "647", "AMNT": "588224.000000", "AVG": "9.101000" }, { "MINT": "810", "NOW": "9.090000", "VOLM": "351", "AMNT": "319136.000000", "AVG": "9.101000" }, { "MINT": "811", "NOW": "9.090000", "VOLM": "792", "AMNT": "720048.000000", "AVG": "9.101000" }, { "MINT": "812", "NOW": "9.090000", "VOLM": "679", "AMNT": "617408.000000", "AVG": "9.101000" }, { "MINT": "813", "NOW": "9.090000", "VOLM": "951", "AMNT": "864672.000000", "AVG": "9.101000" }, { "MINT": "814", "NOW": "9.090000", "VOLM": "2860", "AMNT": "2600000.000000", "AVG": "9.101000" }, { "MINT": "815", "NOW": "9.090000", "VOLM": "683", "AMNT": "620992.000000", "AVG": "9.101000" }, { "MINT": "816", "NOW": "9.090000", "VOLM": "956", "AMNT": "869248.000000", "AVG": "9.101000" }, { "MINT": "817", "NOW": "9.090000", "VOLM": "501", "AMNT": "455648.000000", "AVG": "9.101000" }, { "MINT": "818", "NOW": "9.100000", "VOLM": "379", "AMNT": "344816.000000", "AVG": "9.101000" }, { "MINT": "819", "NOW": "9.100000", "VOLM": "411", "AMNT": "373728.000000", "AVG": "9.101000" }, { "MINT": "820", "NOW": "9.090000", "VOLM": "696", "AMNT": "633088.000000", "AVG": "9.101000" }, { "MINT": "821", "NOW": "9.100000", "VOLM": "885", "AMNT": "804096.000000", "AVG": "9.101000" }, { "MINT": "822", "NOW": "9.090000", "VOLM": "2118", "AMNT": "1925872.000000", "AVG": "9.101000" }, { "MINT": "823", "NOW": "9.090000", "VOLM": "1109", "AMNT": "1007776.000000", "AVG": "9.101000" }, { "MINT": "824", "NOW": "9.100000", "VOLM": "796", "AMNT": "724512.000000", "AVG": "9.101000" }, { "MINT": "825", "NOW": "9.100000", "VOLM": "579", "AMNT": "526528.000000", "AVG": "9.101000" }, { "MINT": "826", "NOW": "9.090000", "VOLM": "1055", "AMNT": "959152.000000", "AVG": "9.101000" }, { "MINT": "827", "NOW": "9.090000", "VOLM": "748", "AMNT": "679584.000000", "AVG": "9.101000" }, { "MINT": "828", "NOW": "9.100000", "VOLM": "1043", "AMNT": "948352.000000", "AVG": "9.100000" }, { "MINT": "829", "NOW": "9.090000", "VOLM": "1025", "AMNT": "931840.000000", "AVG": "9.100000" }, { "MINT": "830", "NOW": "9.090000", "VOLM": "1365", "AMNT": "1241312.000000", "AVG": "9.100000" }, { "MINT": "831", "NOW": "9.090000", "VOLM": "4193", "AMNT": "3815152.000000", "AVG": "9.100000" }, { "MINT": "832", "NOW": "9.100000", "VOLM": "3950", "AMNT": "3593984.000000", "AVG": "9.100000" }, { "MINT": "833", "NOW": "9.100000", "VOLM": "1106", "AMNT": "1006720.000000", "AVG": "9.100000" }, { "MINT": "834", "NOW": "9.100000", "VOLM": "692", "AMNT": "629616.000000", "AVG": "9.100000" }, { "MINT": "835", "NOW": "9.110000", "VOLM": "370", "AMNT": "336864.000000", "AVG": "9.100000" }, { "MINT": "836", "NOW": "9.110000", "VOLM": "682", "AMNT": "621104.000000", "AVG": "9.100000" }, { "MINT": "837", "NOW": "9.100000", "VOLM": "756", "AMNT": "688240.000000", "AVG": "9.100000" }, { "MINT": "838", "NOW": "9.100000", "VOLM": "588", "AMNT": "535392.000000", "AVG": "9.100000" }, { "MINT": "839", "NOW": "9.100000", "VOLM": "1489", "AMNT": "1355184.000000", "AVG": "9.100000" }, { "MINT": "840", "NOW": "9.110000", "VOLM": "859", "AMNT": "781856.000000", "AVG": "9.100000" }, { "MINT": "841", "NOW": "9.110000", "VOLM": "1702", "AMNT": "1548992.000000", "AVG": "9.100000" }, { "MINT": "842", "NOW": "9.110000", "VOLM": "487", "AMNT": "443280.000000", "AVG": "9.100000" }, { "MINT": "843", "NOW": "9.110000", "VOLM": "1445", "AMNT": "1316080.000000", "AVG": "9.100000" }, { "MINT": "844", "NOW": "9.100000", "VOLM": "319", "AMNT": "290336.000000", "AVG": "9.100000" }, { "MINT": "845", "NOW": "9.100000", "VOLM": "413", "AMNT": "376160.000000", "AVG": "9.100000" }, { "MINT": "846", "NOW": "9.100000", "VOLM": "438", "AMNT": "398592.000000", "AVG": "9.100000" }, { "MINT": "847", "NOW": "9.100000", "VOLM": "1192", "AMNT": "1084736.000000", "AVG": "9.100000" }, { "MINT": "848", "NOW": "9.100000", "VOLM": "685", "AMNT": "623600.000000", "AVG": "9.100000" }, { "MINT": "849", "NOW": "9.100000", "VOLM": "254", "AMNT": "230832.000000", "AVG": "9.100000" }, { "MINT": "850", "NOW": "9.090000", "VOLM": "605", "AMNT": "550224.000000", "AVG": "9.100000" }, { "MINT": "851", "NOW": "9.090000", "VOLM": "635", "AMNT": "577680.000000", "AVG": "9.100000" }, { "MINT": "852", "NOW": "9.100000", "VOLM": "812", "AMNT": "738768.000000", "AVG": "9.100000" }, { "MINT": "853", "NOW": "9.100000", "VOLM": "1728", "AMNT": "1572352.000000", "AVG": "9.100000" }, { "MINT": "854", "NOW": "9.090000", "VOLM": "1424", "AMNT": "1295504.000000", "AVG": "9.100000" }, { "MINT": "855", "NOW": "9.100000", "VOLM": "637", "AMNT": "579568.000000", "AVG": "9.100000" }, { "MINT": "856", "NOW": "9.100000", "VOLM": "981", "AMNT": "892592.000000", "AVG": "9.100000" }, { "MINT": "857", "NOW": "9.100000", "VOLM": "2040", "AMNT": "1856320.000000", "AVG": "9.100000" }, { "MINT": "858", "NOW": "9.100000", "VOLM": "2842", "AMNT": "2585632.000000", "AVG": "9.100000" }, { "MINT": "859", "NOW": "9.100000", "VOLM": "890", "AMNT": "810080.000000", "AVG": "9.100000" }, { "MINT": "860", "NOW": "9.100000", "VOLM": "1855", "AMNT": "1689712.000000", "AVG": "9.100000" }, { "MINT": "861", "NOW": "9.100000", "VOLM": "525", "AMNT": "477904.000000", "AVG": "9.100000" }, { "MINT": "862", "NOW": "9.100000", "VOLM": "957", "AMNT": "871584.000000", "AVG": "9.100000" }, { "MINT": "863", "NOW": "9.110000", "VOLM": "772", "AMNT": "703264.000000", "AVG": "9.100000" }, { "MINT": "864", "NOW": "9.110000", "VOLM": "673", "AMNT": "613472.000000", "AVG": "9.100000" }, { "MINT": "865", "NOW": "9.120000", "VOLM": "7197", "AMNT": "6565376.000000", "AVG": "9.101000" }, { "MINT": "866", "NOW": "9.120000", "VOLM": "657", "AMNT": "598848.000000", "AVG": "9.101000" }, { "MINT": "867", "NOW": "9.130000", "VOLM": "1535", "AMNT": "1401376.000000", "AVG": "9.101000" }, { "MINT": "868", "NOW": "9.120000", "VOLM": "3054", "AMNT": "2788192.000000", "AVG": "9.101000" }, { "MINT": "869", "NOW": "9.130000", "VOLM": "853", "AMNT": "778304.000000", "AVG": "9.101000" }, { "MINT": "870", "NOW": "9.120000", "VOLM": "503", "AMNT": "458816.000000", "AVG": "9.101000" }, { "MINT": "871", "NOW": "9.120000", "VOLM": "1086", "AMNT": "991104.000000", "AVG": "9.102000" }, { "MINT": "872", "NOW": "9.130000", "VOLM": "1297", "AMNT": "1183520.000000", "AVG": "9.102000" }, { "MINT": "873", "NOW": "9.130000", "VOLM": "659", "AMNT": "600992.000000", "AVG": "9.102000" }, { "MINT": "874", "NOW": "9.120000", "VOLM": "579", "AMNT": "529120.000000", "AVG": "9.102000" }, { "MINT": "875", "NOW": "9.120000", "VOLM": "890", "AMNT": "812000.000000", "AVG": "9.102000" }, { "MINT": "876", "NOW": "9.120000", "VOLM": "1346", "AMNT": "1228384.000000", "AVG": "9.102000" }, { "MINT": "877", "NOW": "9.130000", "VOLM": "4011", "AMNT": "3661696.000000", "AVG": "9.102000" }, { "MINT": "878", "NOW": "9.140000", "VOLM": "7721", "AMNT": "7056960.000000", "AVG": "9.103000" }, { "MINT": "879", "NOW": "9.130000", "VOLM": "7409", "AMNT": "6772096.000000", "AVG": "9.104000" }, { "MINT": "880", "NOW": "9.140000", "VOLM": "2194", "AMNT": "2004864.000000", "AVG": "9.104000" }, { "MINT": "881", "NOW": "9.140000", "VOLM": "1680", "AMNT": "1535456.000000", "AVG": "9.104000" }, { "MINT": "882", "NOW": "9.140000", "VOLM": "4251", "AMNT": "3883168.000000", "AVG": "9.105000" }, { "MINT": "883", "NOW": "9.150000", "VOLM": "4266", "AMNT": "3899104.000000", "AVG": "9.105000" }, { "MINT": "884", "NOW": "9.140000", "VOLM": "1879", "AMNT": "1718656.000000", "AVG": "9.105000" }, { "MINT": "885", "NOW": "9.150000", "VOLM": "3960", "AMNT": "3622752.000000", "AVG": "9.106000" }, { "MINT": "886", "NOW": "9.140000", "VOLM": "1870", "AMNT": "1710464.000000", "AVG": "9.106000" }, { "MINT": "887", "NOW": "9.140000", "VOLM": "5788", "AMNT": "5291104.000000", "AVG": "9.107000" }, { "MINT": "888", "NOW": "9.140000", "VOLM": "2668", "AMNT": "2438304.000000", "AVG": "9.107000" }, { "MINT": "889", "NOW": "9.130000", "VOLM": "1331", "AMNT": "1216224.000000", "AVG": "9.107000" }, { "MINT": "890", "NOW": "9.130000", "VOLM": "7347", "AMNT": "6708608.000000", "AVG": "9.107000" }, { "MINT": "891", "NOW": "9.120000", "VOLM": "734", "AMNT": "669664.000000", "AVG": "9.107000" }, { "MINT": "892", "NOW": "9.130000", "VOLM": "818", "AMNT": "746528.000000", "AVG": "9.107000" }, { "MINT": "893", "NOW": "9.130000", "VOLM": "2224", "AMNT": "2030176.000000", "AVG": "9.108000" }, { "MINT": "894", "NOW": "9.130000", "VOLM": "538", "AMNT": "491360.000000", "AVG": "9.108000" }, { "MINT": "895", "NOW": "9.130000", "VOLM": "944", "AMNT": "862560.000000", "AVG": "9.108000" }, { "MINT": "896", "NOW": "9.130000", "VOLM": "919", "AMNT": "839136.000000", "AVG": "9.108000" }, { "MINT": "897", "NOW": "9.140000", "VOLM": "1965", "AMNT": "1795104.000000", "AVG": "9.108000" }, { "MINT": "898", "NOW": "9.130000", "VOLM": "10", "AMNT": "9120.000000", "AVG": "9.108000" }, { "MINT": "899", "NOW": "0.000000", "VOLM": "0", "AMNT": "0.000000", "AVG": "0.000000" }, { "MINT": "900", "NOW": "9.130000", "VOLM": "1969", "AMNT": "1798080.000000", "AVG": "9.108000" }] };

exports.default = times;

/***/ }),
/* 41 */
/***/ (function(module, exports, __webpack_require__) {


/**
 * Module dependencies.
 */

var url = __webpack_require__(63);
var parser = __webpack_require__(12);
var Manager = __webpack_require__(23);
var debug = __webpack_require__(7)('socket.io-client');

/**
 * Module exports.
 */

module.exports = exports = lookup;

/**
 * Managers cache.
 */

var cache = exports.managers = {};

/**
 * Looks up an existing `Manager` for multiplexing.
 * If the user summons:
 *
 *   `io('http://localhost/a');`
 *   `io('http://localhost/b');`
 *
 * We reuse the existing instance based on same scheme/port/host,
 * and we initialize sockets for each namespace.
 *
 * @api public
 */

function lookup (uri, opts) {
  if (typeof uri === 'object') {
    opts = uri;
    uri = undefined;
  }

  opts = opts || {};

  var parsed = url(uri);
  var source = parsed.source;
  var id = parsed.id;
  var path = parsed.path;
  var sameNamespace = cache[id] && path in cache[id].nsps;
  var newConnection = opts.forceNew || opts['force new connection'] ||
                      false === opts.multiplex || sameNamespace;

  var io;

  if (newConnection) {
    debug('ignoring socket cache for %s', source);
    io = Manager(source, opts);
  } else {
    if (!cache[id]) {
      debug('new io instance for %s', source);
      cache[id] = Manager(source, opts);
    }
    io = cache[id];
  }
  if (parsed.query && !opts.query) {
    opts.query = parsed.query;
  }
  return io.socket(parsed.path, opts);
}

/**
 * Protocol version.
 *
 * @api public
 */

exports.protocol = parser.protocol;

/**
 * `connect`.
 *
 * @param {String} uri
 * @api public
 */

exports.connect = lookup;

/**
 * Expose constructors for standalone build.
 *
 * @api public
 */

exports.Manager = __webpack_require__(23);
exports.Socket = __webpack_require__(25);


/***/ }),
/* 42 */
/***/ (function(module, exports) {

module.exports = function(module) {
	if(!module.webpackPolyfill) {
		module.deprecate = function() {};
		module.paths = [];
		// module.parent = undefined by default
		if(!module.children) module.children = [];
		Object.defineProperty(module, "loaded", {
			enumerable: true,
			get: function() {
				return module.l;
			}
		});
		Object.defineProperty(module, "id", {
			enumerable: true,
			get: function() {
				return module.i;
			}
		});
		module.webpackPolyfill = 1;
	}
	return module;
};


/***/ }),
/* 43 */
/***/ (function(module, exports) {

module.exports = after

function after(count, callback, err_cb) {
    var bail = false
    err_cb = err_cb || noop
    proxy.count = count

    return (count === 0) ? callback() : proxy

    function proxy(err, result) {
        if (proxy.count <= 0) {
            throw new Error('after called too many times')
        }
        --proxy.count

        // after first error, rest are passed to err_cb
        if (err) {
            bail = true
            callback(err)
            // future error callbacks will go to error handler
            callback = err_cb
        } else if (proxy.count === 0 && !bail) {
            callback(null, result)
        }
    }
}

function noop() {}


/***/ }),
/* 44 */
/***/ (function(module, exports) {

/**
 * An abstraction for slicing an arraybuffer even when
 * ArrayBuffer.prototype.slice is not supported
 *
 * @api public
 */

module.exports = function(arraybuffer, start, end) {
  var bytes = arraybuffer.byteLength;
  start = start || 0;
  end = end || bytes;

  if (arraybuffer.slice) { return arraybuffer.slice(start, end); }

  if (start < 0) { start += bytes; }
  if (end < 0) { end += bytes; }
  if (end > bytes) { end = bytes; }

  if (start >= bytes || start >= end || bytes === 0) {
    return new ArrayBuffer(0);
  }

  var abv = new Uint8Array(arraybuffer);
  var result = new Uint8Array(end - start);
  for (var i = start, ii = 0; i < end; i++, ii++) {
    result[ii] = abv[i];
  }
  return result.buffer;
};


/***/ }),
/* 45 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _Options = __webpack_require__(15);

var _Options2 = _interopRequireDefault(_Options);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var EntitySet = function () {
    function EntitySet(name, option) {
        _classCallCheck(this, EntitySet);

        this.entitys = new Array();
        this.maxIndex = 0;
        this.minIndex = 0;
        this.name = name;
        this.option = option ? option : new _Options2.default();
        this.startIndex = 0;
        this.endIndex = 0;

        this.hightlightIndex = 0;
        this.hightlightX = -101;
        this.hightlightY = 240;
        this.hightlightShow = false;

        this.totalDx = 0;
        this.totalDy = 0;
    }

    _createClass(EntitySet, [{
        key: "push",
        value: function push(e) {
            this.entitys.push(e);
        }
    }, {
        key: "unshift",
        value: function unshift(e) {
            this.entitys.unshift(e);
        }
    }, {
        key: "length",
        value: function length() {
            return this.entitys.length;
        }
    }, {
        key: "getEntity",
        value: function getEntity(index) {
            return this.entitys[index];
        }
    }, {
        key: "getMaxIndex",
        value: function getMaxIndex() {
            this.maxIndex = this.startIndex;
            var entity = this.getEntity(this.maxIndex);
            for (var i = this.startIndex; i < this.endIndex; i++) {
                var entityNext = this.getEntity(i);

                if (i != 0 && Number.parseFloat(entityNext.o) == 0) {
                    var MINT = entityNext.MINT;
                    entityNext = this.getEntity(i - 1);
                    entityNext.MINT = MINT;
                }
                if (Number.parseFloat(entity.h) < Number.parseFloat(entityNext.h)) {
                    this.maxIndex = i;
                    entity = entityNext;
                }
            }
            return this.maxIndex;
        }
    }, {
        key: "getMinIndex",
        value: function getMinIndex() {
            this.minIndex = this.startIndex;
            var entity = this.getEntity(this.minIndex);
            for (var i = this.startIndex; i < this.endIndex; i++) {
                var entityNext = this.getEntity(i);

                if (i != 0 && Number.parseFloat(entityNext.o) == 0) {
                    var preEntity = this.getEntity(i - 1);
                    entityNext.o = preEntity.o;
                    entityNext.h = preEntity.h;
                    entityNext.l = preEntity.l;
                    entityNext.c = preEntity.c;
                    entityNext.v = preEntity.v;
                    entityNext.AMNT = preEntity.AMNT;
                    entityNext.AVG = preEntity.AVG;
                }
                if (Number.parseFloat(entity.l) > Number.parseFloat(entityNext.l)) {
                    this.minIndex = i;
                    entity = entityNext;
                }
            }
            return this.minIndex;
        }
    }, {
        key: "getMaxEntity",
        value: function getMaxEntity() {
            return this.getEntity(this.getMaxIndex());
        }
    }, {
        key: "getMinEntity",
        value: function getMinEntity() {
            return this.getEntity(this.getMinIndex());
        }
    }, {
        key: "getMaxVolIndex",
        value: function getMaxVolIndex() {
            this.maxIndex = this.startIndex;
            var entity = this.getEntity(this.maxIndex);
            for (var i = this.startIndex; i < this.endIndex; i++) {
                var entityNext = this.getEntity(i);
                if (Number.parseFloat(entity.v) < Number.parseFloat(entityNext.v)) {
                    this.maxIndex = i;
                    entity = entityNext;
                }
            }
            return this.maxIndex;
        }
    }, {
        key: "getMaxVolEntity",
        value: function getMaxVolEntity() {
            return this.getEntity(this.getMaxVolIndex());
        }
    }, {
        key: "getAVG",
        value: function getAVG(index, type, perid) {
            if (!this.getEntity(index)) {
                return;
            }
            var avg = 0;
            switch (type) {
                case "c":
                    //收盘价
                    if (index < perid) {
                        avg = 0;
                    } else {
                        for (var i = index - perid; i < index; i++) {
                            avg += Number.parseFloat(this.getEntity(i).c);
                        }
                        avg = avg / perid;
                    }
                    break;
                case "v":
                    //量
                    if (index < perid) {
                        avg = 0;
                    } else {
                        for (var _i = index - perid; _i < index; _i++) {
                            avg += Number.parseFloat(this.getEntity(_i).v);
                        }
                        avg = avg / perid;
                    }
                    break;
                case "n":
                    //当前价

                    avg = Number.parseFloat(this.getEntity(index).AVG);
                    break;
            }
            return avg;
        }
    }]);

    return EntitySet;
}();

exports.default = EntitySet;

/***/ }),
/* 46 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
    value: true
});

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var RectF = function RectF(left, top, right, bottom) {
    _classCallCheck(this, RectF);

    this.left = left;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
};

exports.default = RectF;

/***/ }),
/* 47 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _KLineRender = __webpack_require__(34);

var _KLineRender2 = _interopRequireDefault(_KLineRender);

var _BaseChart = __webpack_require__(29);

var _BaseChart2 = _interopRequireDefault(_BaseChart);

var _KEntity = __webpack_require__(32);

var _KEntity2 = _interopRequireDefault(_KEntity);

var _KLineDrawing = __webpack_require__(33);

var _KLineDrawing2 = _interopRequireDefault(_KLineDrawing);

var _klinedata = __webpack_require__(39);

var _klinedata2 = _interopRequireDefault(_klinedata);

var _timedata = __webpack_require__(40);

var _timedata2 = _interopRequireDefault(_timedata);

var _IndexRender = __webpack_require__(31);

var _IndexRender2 = _interopRequireDefault(_IndexRender);

var _VOLDrawing = __webpack_require__(38);

var _VOLDrawing2 = _interopRequireDefault(_VOLDrawing);

var _AvgDrawing = __webpack_require__(28);

var _AvgDrawing2 = _interopRequireDefault(_AvgDrawing);

var _TimeRender = __webpack_require__(13);

var _TimeRender2 = _interopRequireDefault(_TimeRender);

var _TimeEntity = __webpack_require__(36);

var _TimeEntity2 = _interopRequireDefault(_TimeEntity);

var _TimeDrawing = __webpack_require__(35);

var _TimeDrawing2 = _interopRequireDefault(_TimeDrawing);

var _TimeIndexRender = __webpack_require__(37);

var _TimeIndexRender2 = _interopRequireDefault(_TimeIndexRender);

var _CrossDrawing = __webpack_require__(30);

var _CrossDrawing2 = _interopRequireDefault(_CrossDrawing);

var _socket = __webpack_require__(41);

var _socket2 = _interopRequireDefault(_socket);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var chart = new _BaseChart2.default("#canvas");
chart.resizeCanvas();

var render = new _KLineRender2.default("kl");
// render.margin = [10, 10, 20, 20]
render.viewport = { right: 1, bottom: 200 };
render.padding = [0, 40, 0, 40];
// render.option.weight = 1

new _KLineDrawing2.default(render);
new _CrossDrawing2.default(render);
new _AvgDrawing2.default(render, "c", 5, "#508ce7", 1);
new _AvgDrawing2.default(render, "c", 10, "#ec9917", 1);
new _AvgDrawing2.default(render, "c", 20, "#e339dd", 1);
new _AvgDrawing2.default(render, "c", 60, "#21a764", 1);

var list = _klinedata2.default.LIST;
var _iteratorNormalCompletion = true;
var _didIteratorError = false;
var _iteratorError = undefined;

try {
    for (var _iterator = list[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
        var entity = _step.value;

        render.addToEnd(new _KEntity2.default(entity.OPEN, entity.HIGH, entity.LOW, entity.CLOS, entity.VOLM));
    }
} catch (err) {
    _didIteratorError = true;
    _iteratorError = err;
} finally {
    try {
        if (!_iteratorNormalCompletion && _iterator.return) {
            _iterator.return();
        }
    } finally {
        if (_didIteratorError) {
            throw _iteratorError;
        }
    }
}

var indexRender = new _IndexRender2.default("index");
indexRender.viewport = { right: 1, bottom: 80 };
indexRender.margin = [0, 20, 0, 0];
indexRender.padding = [0, 10, 0, 0];
indexRender.entitySet = render.entitySet;
new _VOLDrawing2.default(indexRender);
new _CrossDrawing2.default(indexRender);
new _AvgDrawing2.default(indexRender, "v", 5, "#508ce7", 1.5);
new _AvgDrawing2.default(indexRender, "v", 10, "#ec9917", 1.5);

var timeRender = new _TimeRender2.default("time");
timeRender.viewport = { right: 1, bottom: 100 };
timeRender.margin = [0, 20, 0, 0];
timeRender.padding = [0, 20, 0, 20];
new _AvgDrawing2.default(timeRender, "n", 1, "#ec9917", 1);

var timeIndexRender = new _TimeIndexRender2.default("timeIndex");
timeIndexRender.viewport = { right: 1, bottom: 40 };
timeIndexRender.margin = [0, 0, 0, 0];
timeIndexRender.padding = [0, 10, 0, 0];
timeIndexRender.entitySet = timeRender.entitySet;
new _VOLDrawing2.default(timeIndexRender, "timeline");

var times = _timedata2.default.LIST;
var _iteratorNormalCompletion2 = true;
var _didIteratorError2 = false;
var _iteratorError2 = undefined;

try {
    for (var _iterator2 = times[Symbol.iterator](), _step2; !(_iteratorNormalCompletion2 = (_step2 = _iterator2.next()).done); _iteratorNormalCompletion2 = true) {
        var _entity = _step2.value;

        timeRender.addToEnd(new _TimeEntity2.default(_entity.MINT, _entity.NOW, _entity.VOLM, _entity.AMNT, _entity.AVG));
    }
} catch (err) {
    _didIteratorError2 = true;
    _iteratorError2 = err;
} finally {
    try {
        if (!_iteratorNormalCompletion2 && _iterator2.return) {
            _iterator2.return();
        }
    } finally {
        if (_didIteratorError2) {
            throw _iteratorError2;
        }
    }
}

new _TimeDrawing2.default(timeRender);

chart.addRender(render);
chart.addRender(indexRender);
chart.addRender(timeRender);
chart.addRender(timeIndexRender);
chart.render

// let index = 0
// let hander = setInterval(function() {
//     let entity = times[index++]
//     timeRender.addToEnd(new TimeEntity(entity.MINT, entity.NOW, entity.VOLM, entity.AMNT, entity.AVG))
//     if (index >= 240) {
//         clearInterval(hander)
//     }
// }, 100)

();var socket = _socket2.default.connect("https://uatwebhq.shaomaicaibo.com:443");
socket.on("connection", function (resp) {
    window.resp = resp;
    console.log(resp);
});

/***/ }),
/* 48 */
/***/ (function(module, exports) {


/**
 * Expose `Backoff`.
 */

module.exports = Backoff;

/**
 * Initialize backoff timer with `opts`.
 *
 * - `min` initial timeout in milliseconds [100]
 * - `max` max timeout [10000]
 * - `jitter` [0]
 * - `factor` [2]
 *
 * @param {Object} opts
 * @api public
 */

function Backoff(opts) {
  opts = opts || {};
  this.ms = opts.min || 100;
  this.max = opts.max || 10000;
  this.factor = opts.factor || 2;
  this.jitter = opts.jitter > 0 && opts.jitter <= 1 ? opts.jitter : 0;
  this.attempts = 0;
}

/**
 * Return the backoff duration.
 *
 * @return {Number}
 * @api public
 */

Backoff.prototype.duration = function(){
  var ms = this.ms * Math.pow(this.factor, this.attempts++);
  if (this.jitter) {
    var rand =  Math.random();
    var deviation = Math.floor(rand * this.jitter * ms);
    ms = (Math.floor(rand * 10) & 1) == 0  ? ms - deviation : ms + deviation;
  }
  return Math.min(ms, this.max) | 0;
};

/**
 * Reset the number of attempts.
 *
 * @api public
 */

Backoff.prototype.reset = function(){
  this.attempts = 0;
};

/**
 * Set the minimum duration
 *
 * @api public
 */

Backoff.prototype.setMin = function(min){
  this.ms = min;
};

/**
 * Set the maximum duration
 *
 * @api public
 */

Backoff.prototype.setMax = function(max){
  this.max = max;
};

/**
 * Set the jitter
 *
 * @api public
 */

Backoff.prototype.setJitter = function(jitter){
  this.jitter = jitter;
};



/***/ }),
/* 49 */
/***/ (function(module, exports) {

/*
 * base64-arraybuffer
 * https://github.com/niklasvh/base64-arraybuffer
 *
 * Copyright (c) 2012 Niklas von Hertzen
 * Licensed under the MIT license.
 */
(function(){
  "use strict";

  var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

  // Use a lookup table to find the index.
  var lookup = new Uint8Array(256);
  for (var i = 0; i < chars.length; i++) {
    lookup[chars.charCodeAt(i)] = i;
  }

  exports.encode = function(arraybuffer) {
    var bytes = new Uint8Array(arraybuffer),
    i, len = bytes.length, base64 = "";

    for (i = 0; i < len; i+=3) {
      base64 += chars[bytes[i] >> 2];
      base64 += chars[((bytes[i] & 3) << 4) | (bytes[i + 1] >> 4)];
      base64 += chars[((bytes[i + 1] & 15) << 2) | (bytes[i + 2] >> 6)];
      base64 += chars[bytes[i + 2] & 63];
    }

    if ((len % 3) === 2) {
      base64 = base64.substring(0, base64.length - 1) + "=";
    } else if (len % 3 === 1) {
      base64 = base64.substring(0, base64.length - 2) + "==";
    }

    return base64;
  };

  exports.decode =  function(base64) {
    var bufferLength = base64.length * 0.75,
    len = base64.length, i, p = 0,
    encoded1, encoded2, encoded3, encoded4;

    if (base64[base64.length - 1] === "=") {
      bufferLength--;
      if (base64[base64.length - 2] === "=") {
        bufferLength--;
      }
    }

    var arraybuffer = new ArrayBuffer(bufferLength),
    bytes = new Uint8Array(arraybuffer);

    for (i = 0; i < len; i+=4) {
      encoded1 = lookup[base64.charCodeAt(i)];
      encoded2 = lookup[base64.charCodeAt(i+1)];
      encoded3 = lookup[base64.charCodeAt(i+2)];
      encoded4 = lookup[base64.charCodeAt(i+3)];

      bytes[p++] = (encoded1 << 2) | (encoded2 >> 4);
      bytes[p++] = ((encoded2 & 15) << 4) | (encoded3 >> 2);
      bytes[p++] = ((encoded3 & 3) << 6) | (encoded4 & 63);
    }

    return arraybuffer;
  };
})();


/***/ }),
/* 50 */
/***/ (function(module, exports, __webpack_require__) {

/* WEBPACK VAR INJECTION */(function(global) {/**
 * Create a blob builder even when vendor prefixes exist
 */

var BlobBuilder = global.BlobBuilder
  || global.WebKitBlobBuilder
  || global.MSBlobBuilder
  || global.MozBlobBuilder;

/**
 * Check if Blob constructor is supported
 */

var blobSupported = (function() {
  try {
    var a = new Blob(['hi']);
    return a.size === 2;
  } catch(e) {
    return false;
  }
})();

/**
 * Check if Blob constructor supports ArrayBufferViews
 * Fails in Safari 6, so we need to map to ArrayBuffers there.
 */

var blobSupportsArrayBufferView = blobSupported && (function() {
  try {
    var b = new Blob([new Uint8Array([1,2])]);
    return b.size === 2;
  } catch(e) {
    return false;
  }
})();

/**
 * Check if BlobBuilder is supported
 */

var blobBuilderSupported = BlobBuilder
  && BlobBuilder.prototype.append
  && BlobBuilder.prototype.getBlob;

/**
 * Helper function that maps ArrayBufferViews to ArrayBuffers
 * Used by BlobBuilder constructor and old browsers that didn't
 * support it in the Blob constructor.
 */

function mapArrayBufferViews(ary) {
  for (var i = 0; i < ary.length; i++) {
    var chunk = ary[i];
    if (chunk.buffer instanceof ArrayBuffer) {
      var buf = chunk.buffer;

      // if this is a subarray, make a copy so we only
      // include the subarray region from the underlying buffer
      if (chunk.byteLength !== buf.byteLength) {
        var copy = new Uint8Array(chunk.byteLength);
        copy.set(new Uint8Array(buf, chunk.byteOffset, chunk.byteLength));
        buf = copy.buffer;
      }

      ary[i] = buf;
    }
  }
}

function BlobBuilderConstructor(ary, options) {
  options = options || {};

  var bb = new BlobBuilder();
  mapArrayBufferViews(ary);

  for (var i = 0; i < ary.length; i++) {
    bb.append(ary[i]);
  }

  return (options.type) ? bb.getBlob(options.type) : bb.getBlob();
};

function BlobConstructor(ary, options) {
  mapArrayBufferViews(ary);
  return new Blob(ary, options || {});
};

module.exports = (function() {
  if (blobSupported) {
    return blobSupportsArrayBufferView ? global.Blob : BlobConstructor;
  } else if (blobBuilderSupported) {
    return BlobBuilderConstructor;
  } else {
    return undefined;
  }
})();

/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(0)))

/***/ }),
/* 51 */
/***/ (function(module, exports, __webpack_require__) {


module.exports = __webpack_require__(52);


/***/ }),
/* 52 */
/***/ (function(module, exports, __webpack_require__) {


module.exports = __webpack_require__(53);

/**
 * Exports parser
 *
 * @api public
 *
 */
module.exports.parser = __webpack_require__(2);


/***/ }),
/* 53 */
/***/ (function(module, exports, __webpack_require__) {

/* WEBPACK VAR INJECTION */(function(global) {/**
 * Module dependencies.
 */

var transports = __webpack_require__(17);
var Emitter = __webpack_require__(1);
var debug = __webpack_require__(5)('engine.io-client:socket');
var index = __webpack_require__(20);
var parser = __webpack_require__(2);
var parseuri = __webpack_require__(22);
var parsejson = __webpack_require__(62);
var parseqs = __webpack_require__(6);

/**
 * Module exports.
 */

module.exports = Socket;

/**
 * Socket constructor.
 *
 * @param {String|Object} uri or options
 * @param {Object} options
 * @api public
 */

function Socket (uri, opts) {
  if (!(this instanceof Socket)) return new Socket(uri, opts);

  opts = opts || {};

  if (uri && 'object' === typeof uri) {
    opts = uri;
    uri = null;
  }

  if (uri) {
    uri = parseuri(uri);
    opts.hostname = uri.host;
    opts.secure = uri.protocol === 'https' || uri.protocol === 'wss';
    opts.port = uri.port;
    if (uri.query) opts.query = uri.query;
  } else if (opts.host) {
    opts.hostname = parseuri(opts.host).host;
  }

  this.secure = null != opts.secure ? opts.secure
    : (global.location && 'https:' === location.protocol);

  if (opts.hostname && !opts.port) {
    // if no port is specified manually, use the protocol default
    opts.port = this.secure ? '443' : '80';
  }

  this.agent = opts.agent || false;
  this.hostname = opts.hostname ||
    (global.location ? location.hostname : 'localhost');
  this.port = opts.port || (global.location && location.port
      ? location.port
      : (this.secure ? 443 : 80));
  this.query = opts.query || {};
  if ('string' === typeof this.query) this.query = parseqs.decode(this.query);
  this.upgrade = false !== opts.upgrade;
  this.path = (opts.path || '/engine.io').replace(/\/$/, '') + '/';
  this.forceJSONP = !!opts.forceJSONP;
  this.jsonp = false !== opts.jsonp;
  this.forceBase64 = !!opts.forceBase64;
  this.enablesXDR = !!opts.enablesXDR;
  this.timestampParam = opts.timestampParam || 't';
  this.timestampRequests = opts.timestampRequests;
  this.transports = opts.transports || ['polling', 'websocket'];
  this.transportOptions = opts.transportOptions || {};
  this.readyState = '';
  this.writeBuffer = [];
  this.prevBufferLen = 0;
  this.policyPort = opts.policyPort || 843;
  this.rememberUpgrade = opts.rememberUpgrade || false;
  this.binaryType = null;
  this.onlyBinaryUpgrades = opts.onlyBinaryUpgrades;
  this.perMessageDeflate = false !== opts.perMessageDeflate ? (opts.perMessageDeflate || {}) : false;

  if (true === this.perMessageDeflate) this.perMessageDeflate = {};
  if (this.perMessageDeflate && null == this.perMessageDeflate.threshold) {
    this.perMessageDeflate.threshold = 1024;
  }

  // SSL options for Node.js client
  this.pfx = opts.pfx || null;
  this.key = opts.key || null;
  this.passphrase = opts.passphrase || null;
  this.cert = opts.cert || null;
  this.ca = opts.ca || null;
  this.ciphers = opts.ciphers || null;
  this.rejectUnauthorized = opts.rejectUnauthorized === undefined ? true : opts.rejectUnauthorized;
  this.forceNode = !!opts.forceNode;

  // other options for Node.js client
  var freeGlobal = typeof global === 'object' && global;
  if (freeGlobal.global === freeGlobal) {
    if (opts.extraHeaders && Object.keys(opts.extraHeaders).length > 0) {
      this.extraHeaders = opts.extraHeaders;
    }

    if (opts.localAddress) {
      this.localAddress = opts.localAddress;
    }
  }

  // set on handshake
  this.id = null;
  this.upgrades = null;
  this.pingInterval = null;
  this.pingTimeout = null;

  // set on heartbeat
  this.pingIntervalTimer = null;
  this.pingTimeoutTimer = null;

  this.open();
}

Socket.priorWebsocketSuccess = false;

/**
 * Mix in `Emitter`.
 */

Emitter(Socket.prototype);

/**
 * Protocol version.
 *
 * @api public
 */

Socket.protocol = parser.protocol; // this is an int

/**
 * Expose deps for legacy compatibility
 * and standalone browser access.
 */

Socket.Socket = Socket;
Socket.Transport = __webpack_require__(10);
Socket.transports = __webpack_require__(17);
Socket.parser = __webpack_require__(2);

/**
 * Creates transport of the given type.
 *
 * @param {String} transport name
 * @return {Transport}
 * @api private
 */

Socket.prototype.createTransport = function (name) {
  debug('creating transport "%s"', name);
  var query = clone(this.query);

  // append engine.io protocol identifier
  query.EIO = parser.protocol;

  // transport name
  query.transport = name;

  // per-transport options
  var options = this.transportOptions[name] || {};

  // session id if we already have one
  if (this.id) query.sid = this.id;

  var transport = new transports[name]({
    query: query,
    socket: this,
    agent: options.agent || this.agent,
    hostname: options.hostname || this.hostname,
    port: options.port || this.port,
    secure: options.secure || this.secure,
    path: options.path || this.path,
    forceJSONP: options.forceJSONP || this.forceJSONP,
    jsonp: options.jsonp || this.jsonp,
    forceBase64: options.forceBase64 || this.forceBase64,
    enablesXDR: options.enablesXDR || this.enablesXDR,
    timestampRequests: options.timestampRequests || this.timestampRequests,
    timestampParam: options.timestampParam || this.timestampParam,
    policyPort: options.policyPort || this.policyPort,
    pfx: options.pfx || this.pfx,
    key: options.key || this.key,
    passphrase: options.passphrase || this.passphrase,
    cert: options.cert || this.cert,
    ca: options.ca || this.ca,
    ciphers: options.ciphers || this.ciphers,
    rejectUnauthorized: options.rejectUnauthorized || this.rejectUnauthorized,
    perMessageDeflate: options.perMessageDeflate || this.perMessageDeflate,
    extraHeaders: options.extraHeaders || this.extraHeaders,
    forceNode: options.forceNode || this.forceNode,
    localAddress: options.localAddress || this.localAddress,
    requestTimeout: options.requestTimeout || this.requestTimeout,
    protocols: options.protocols || void (0)
  });

  return transport;
};

function clone (obj) {
  var o = {};
  for (var i in obj) {
    if (obj.hasOwnProperty(i)) {
      o[i] = obj[i];
    }
  }
  return o;
}

/**
 * Initializes transport to use and starts probe.
 *
 * @api private
 */
Socket.prototype.open = function () {
  var transport;
  if (this.rememberUpgrade && Socket.priorWebsocketSuccess && this.transports.indexOf('websocket') !== -1) {
    transport = 'websocket';
  } else if (0 === this.transports.length) {
    // Emit error on next tick so it can be listened to
    var self = this;
    setTimeout(function () {
      self.emit('error', 'No transports available');
    }, 0);
    return;
  } else {
    transport = this.transports[0];
  }
  this.readyState = 'opening';

  // Retry with the next transport if the transport is disabled (jsonp: false)
  try {
    transport = this.createTransport(transport);
  } catch (e) {
    this.transports.shift();
    this.open();
    return;
  }

  transport.open();
  this.setTransport(transport);
};

/**
 * Sets the current transport. Disables the existing one (if any).
 *
 * @api private
 */

Socket.prototype.setTransport = function (transport) {
  debug('setting transport %s', transport.name);
  var self = this;

  if (this.transport) {
    debug('clearing existing transport %s', this.transport.name);
    this.transport.removeAllListeners();
  }

  // set up transport
  this.transport = transport;

  // set up transport listeners
  transport
  .on('drain', function () {
    self.onDrain();
  })
  .on('packet', function (packet) {
    self.onPacket(packet);
  })
  .on('error', function (e) {
    self.onError(e);
  })
  .on('close', function () {
    self.onClose('transport close');
  });
};

/**
 * Probes a transport.
 *
 * @param {String} transport name
 * @api private
 */

Socket.prototype.probe = function (name) {
  debug('probing transport "%s"', name);
  var transport = this.createTransport(name, { probe: 1 });
  var failed = false;
  var self = this;

  Socket.priorWebsocketSuccess = false;

  function onTransportOpen () {
    if (self.onlyBinaryUpgrades) {
      var upgradeLosesBinary = !this.supportsBinary && self.transport.supportsBinary;
      failed = failed || upgradeLosesBinary;
    }
    if (failed) return;

    debug('probe transport "%s" opened', name);
    transport.send([{ type: 'ping', data: 'probe' }]);
    transport.once('packet', function (msg) {
      if (failed) return;
      if ('pong' === msg.type && 'probe' === msg.data) {
        debug('probe transport "%s" pong', name);
        self.upgrading = true;
        self.emit('upgrading', transport);
        if (!transport) return;
        Socket.priorWebsocketSuccess = 'websocket' === transport.name;

        debug('pausing current transport "%s"', self.transport.name);
        self.transport.pause(function () {
          if (failed) return;
          if ('closed' === self.readyState) return;
          debug('changing transport and sending upgrade packet');

          cleanup();

          self.setTransport(transport);
          transport.send([{ type: 'upgrade' }]);
          self.emit('upgrade', transport);
          transport = null;
          self.upgrading = false;
          self.flush();
        });
      } else {
        debug('probe transport "%s" failed', name);
        var err = new Error('probe error');
        err.transport = transport.name;
        self.emit('upgradeError', err);
      }
    });
  }

  function freezeTransport () {
    if (failed) return;

    // Any callback called by transport should be ignored since now
    failed = true;

    cleanup();

    transport.close();
    transport = null;
  }

  // Handle any error that happens while probing
  function onerror (err) {
    var error = new Error('probe error: ' + err);
    error.transport = transport.name;

    freezeTransport();

    debug('probe transport "%s" failed because of error: %s', name, err);

    self.emit('upgradeError', error);
  }

  function onTransportClose () {
    onerror('transport closed');
  }

  // When the socket is closed while we're probing
  function onclose () {
    onerror('socket closed');
  }

  // When the socket is upgraded while we're probing
  function onupgrade (to) {
    if (transport && to.name !== transport.name) {
      debug('"%s" works - aborting "%s"', to.name, transport.name);
      freezeTransport();
    }
  }

  // Remove all listeners on the transport and on self
  function cleanup () {
    transport.removeListener('open', onTransportOpen);
    transport.removeListener('error', onerror);
    transport.removeListener('close', onTransportClose);
    self.removeListener('close', onclose);
    self.removeListener('upgrading', onupgrade);
  }

  transport.once('open', onTransportOpen);
  transport.once('error', onerror);
  transport.once('close', onTransportClose);

  this.once('close', onclose);
  this.once('upgrading', onupgrade);

  transport.open();
};

/**
 * Called when connection is deemed open.
 *
 * @api public
 */

Socket.prototype.onOpen = function () {
  debug('socket open');
  this.readyState = 'open';
  Socket.priorWebsocketSuccess = 'websocket' === this.transport.name;
  this.emit('open');
  this.flush();

  // we check for `readyState` in case an `open`
  // listener already closed the socket
  if ('open' === this.readyState && this.upgrade && this.transport.pause) {
    debug('starting upgrade probes');
    for (var i = 0, l = this.upgrades.length; i < l; i++) {
      this.probe(this.upgrades[i]);
    }
  }
};

/**
 * Handles a packet.
 *
 * @api private
 */

Socket.prototype.onPacket = function (packet) {
  if ('opening' === this.readyState || 'open' === this.readyState ||
      'closing' === this.readyState) {
    debug('socket receive: type "%s", data "%s"', packet.type, packet.data);

    this.emit('packet', packet);

    // Socket is live - any packet counts
    this.emit('heartbeat');

    switch (packet.type) {
      case 'open':
        this.onHandshake(parsejson(packet.data));
        break;

      case 'pong':
        this.setPing();
        this.emit('pong');
        break;

      case 'error':
        var err = new Error('server error');
        err.code = packet.data;
        this.onError(err);
        break;

      case 'message':
        this.emit('data', packet.data);
        this.emit('message', packet.data);
        break;
    }
  } else {
    debug('packet received with socket readyState "%s"', this.readyState);
  }
};

/**
 * Called upon handshake completion.
 *
 * @param {Object} handshake obj
 * @api private
 */

Socket.prototype.onHandshake = function (data) {
  this.emit('handshake', data);
  this.id = data.sid;
  this.transport.query.sid = data.sid;
  this.upgrades = this.filterUpgrades(data.upgrades);
  this.pingInterval = data.pingInterval;
  this.pingTimeout = data.pingTimeout;
  this.onOpen();
  // In case open handler closes socket
  if ('closed' === this.readyState) return;
  this.setPing();

  // Prolong liveness of socket on heartbeat
  this.removeListener('heartbeat', this.onHeartbeat);
  this.on('heartbeat', this.onHeartbeat);
};

/**
 * Resets ping timeout.
 *
 * @api private
 */

Socket.prototype.onHeartbeat = function (timeout) {
  clearTimeout(this.pingTimeoutTimer);
  var self = this;
  self.pingTimeoutTimer = setTimeout(function () {
    if ('closed' === self.readyState) return;
    self.onClose('ping timeout');
  }, timeout || (self.pingInterval + self.pingTimeout));
};

/**
 * Pings server every `this.pingInterval` and expects response
 * within `this.pingTimeout` or closes connection.
 *
 * @api private
 */

Socket.prototype.setPing = function () {
  var self = this;
  clearTimeout(self.pingIntervalTimer);
  self.pingIntervalTimer = setTimeout(function () {
    debug('writing ping packet - expecting pong within %sms', self.pingTimeout);
    self.ping();
    self.onHeartbeat(self.pingTimeout);
  }, self.pingInterval);
};

/**
* Sends a ping packet.
*
* @api private
*/

Socket.prototype.ping = function () {
  var self = this;
  this.sendPacket('ping', function () {
    self.emit('ping');
  });
};

/**
 * Called on `drain` event
 *
 * @api private
 */

Socket.prototype.onDrain = function () {
  this.writeBuffer.splice(0, this.prevBufferLen);

  // setting prevBufferLen = 0 is very important
  // for example, when upgrading, upgrade packet is sent over,
  // and a nonzero prevBufferLen could cause problems on `drain`
  this.prevBufferLen = 0;

  if (0 === this.writeBuffer.length) {
    this.emit('drain');
  } else {
    this.flush();
  }
};

/**
 * Flush write buffers.
 *
 * @api private
 */

Socket.prototype.flush = function () {
  if ('closed' !== this.readyState && this.transport.writable &&
    !this.upgrading && this.writeBuffer.length) {
    debug('flushing %d packets in socket', this.writeBuffer.length);
    this.transport.send(this.writeBuffer);
    // keep track of current length of writeBuffer
    // splice writeBuffer and callbackBuffer on `drain`
    this.prevBufferLen = this.writeBuffer.length;
    this.emit('flush');
  }
};

/**
 * Sends a message.
 *
 * @param {String} message.
 * @param {Function} callback function.
 * @param {Object} options.
 * @return {Socket} for chaining.
 * @api public
 */

Socket.prototype.write =
Socket.prototype.send = function (msg, options, fn) {
  this.sendPacket('message', msg, options, fn);
  return this;
};

/**
 * Sends a packet.
 *
 * @param {String} packet type.
 * @param {String} data.
 * @param {Object} options.
 * @param {Function} callback function.
 * @api private
 */

Socket.prototype.sendPacket = function (type, data, options, fn) {
  if ('function' === typeof data) {
    fn = data;
    data = undefined;
  }

  if ('function' === typeof options) {
    fn = options;
    options = null;
  }

  if ('closing' === this.readyState || 'closed' === this.readyState) {
    return;
  }

  options = options || {};
  options.compress = false !== options.compress;

  var packet = {
    type: type,
    data: data,
    options: options
  };
  this.emit('packetCreate', packet);
  this.writeBuffer.push(packet);
  if (fn) this.once('flush', fn);
  this.flush();
};

/**
 * Closes the connection.
 *
 * @api private
 */

Socket.prototype.close = function () {
  if ('opening' === this.readyState || 'open' === this.readyState) {
    this.readyState = 'closing';

    var self = this;

    if (this.writeBuffer.length) {
      this.once('drain', function () {
        if (this.upgrading) {
          waitForUpgrade();
        } else {
          close();
        }
      });
    } else if (this.upgrading) {
      waitForUpgrade();
    } else {
      close();
    }
  }

  function close () {
    self.onClose('forced close');
    debug('socket closing - telling transport to close');
    self.transport.close();
  }

  function cleanupAndClose () {
    self.removeListener('upgrade', cleanupAndClose);
    self.removeListener('upgradeError', cleanupAndClose);
    close();
  }

  function waitForUpgrade () {
    // wait for upgrade to finish since we can't send packets while pausing a transport
    self.once('upgrade', cleanupAndClose);
    self.once('upgradeError', cleanupAndClose);
  }

  return this;
};

/**
 * Called upon transport error
 *
 * @api private
 */

Socket.prototype.onError = function (err) {
  debug('socket error %j', err);
  Socket.priorWebsocketSuccess = false;
  this.emit('error', err);
  this.onClose('transport error', err);
};

/**
 * Called upon transport close.
 *
 * @api private
 */

Socket.prototype.onClose = function (reason, desc) {
  if ('opening' === this.readyState || 'open' === this.readyState || 'closing' === this.readyState) {
    debug('socket close with reason: "%s"', reason);
    var self = this;

    // clear timers
    clearTimeout(this.pingIntervalTimer);
    clearTimeout(this.pingTimeoutTimer);

    // stop event from firing again for transport
    this.transport.removeAllListeners('close');

    // ensure transport won't stay open
    this.transport.close();

    // ignore further transport communication
    this.transport.removeAllListeners();

    // set ready state
    this.readyState = 'closed';

    // clear session id
    this.id = null;

    // emit close event
    this.emit('close', reason, desc);

    // clean buffers after, so users can still
    // grab the buffers on `close` event
    self.writeBuffer = [];
    self.prevBufferLen = 0;
  }
};

/**
 * Filters upgrades, returning only those matching client transports.
 *
 * @param {Array} server upgrades
 * @api private
 *
 */

Socket.prototype.filterUpgrades = function (upgrades) {
  var filteredUpgrades = [];
  for (var i = 0, j = upgrades.length; i < j; i++) {
    if (~index(this.transports, upgrades[i])) filteredUpgrades.push(upgrades[i]);
  }
  return filteredUpgrades;
};

/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(0)))

/***/ }),
/* 54 */
/***/ (function(module, exports, __webpack_require__) {

/* WEBPACK VAR INJECTION */(function(global) {
/**
 * Module requirements.
 */

var Polling = __webpack_require__(18);
var inherit = __webpack_require__(4);

/**
 * Module exports.
 */

module.exports = JSONPPolling;

/**
 * Cached regular expressions.
 */

var rNewline = /\n/g;
var rEscapedNewline = /\\n/g;

/**
 * Global JSONP callbacks.
 */

var callbacks;

/**
 * Noop.
 */

function empty () { }

/**
 * JSONP Polling constructor.
 *
 * @param {Object} opts.
 * @api public
 */

function JSONPPolling (opts) {
  Polling.call(this, opts);

  this.query = this.query || {};

  // define global callbacks array if not present
  // we do this here (lazily) to avoid unneeded global pollution
  if (!callbacks) {
    // we need to consider multiple engines in the same page
    if (!global.___eio) global.___eio = [];
    callbacks = global.___eio;
  }

  // callback identifier
  this.index = callbacks.length;

  // add callback to jsonp global
  var self = this;
  callbacks.push(function (msg) {
    self.onData(msg);
  });

  // append to query string
  this.query.j = this.index;

  // prevent spurious errors from being emitted when the window is unloaded
  if (global.document && global.addEventListener) {
    global.addEventListener('beforeunload', function () {
      if (self.script) self.script.onerror = empty;
    }, false);
  }
}

/**
 * Inherits from Polling.
 */

inherit(JSONPPolling, Polling);

/*
 * JSONP only supports binary as base64 encoded strings
 */

JSONPPolling.prototype.supportsBinary = false;

/**
 * Closes the socket.
 *
 * @api private
 */

JSONPPolling.prototype.doClose = function () {
  if (this.script) {
    this.script.parentNode.removeChild(this.script);
    this.script = null;
  }

  if (this.form) {
    this.form.parentNode.removeChild(this.form);
    this.form = null;
    this.iframe = null;
  }

  Polling.prototype.doClose.call(this);
};

/**
 * Starts a poll cycle.
 *
 * @api private
 */

JSONPPolling.prototype.doPoll = function () {
  var self = this;
  var script = document.createElement('script');

  if (this.script) {
    this.script.parentNode.removeChild(this.script);
    this.script = null;
  }

  script.async = true;
  script.src = this.uri();
  script.onerror = function (e) {
    self.onError('jsonp poll error', e);
  };

  var insertAt = document.getElementsByTagName('script')[0];
  if (insertAt) {
    insertAt.parentNode.insertBefore(script, insertAt);
  } else {
    (document.head || document.body).appendChild(script);
  }
  this.script = script;

  var isUAgecko = 'undefined' !== typeof navigator && /gecko/i.test(navigator.userAgent);

  if (isUAgecko) {
    setTimeout(function () {
      var iframe = document.createElement('iframe');
      document.body.appendChild(iframe);
      document.body.removeChild(iframe);
    }, 100);
  }
};

/**
 * Writes with a hidden iframe.
 *
 * @param {String} data to send
 * @param {Function} called upon flush.
 * @api private
 */

JSONPPolling.prototype.doWrite = function (data, fn) {
  var self = this;

  if (!this.form) {
    var form = document.createElement('form');
    var area = document.createElement('textarea');
    var id = this.iframeId = 'eio_iframe_' + this.index;
    var iframe;

    form.className = 'socketio';
    form.style.position = 'absolute';
    form.style.top = '-1000px';
    form.style.left = '-1000px';
    form.target = id;
    form.method = 'POST';
    form.setAttribute('accept-charset', 'utf-8');
    area.name = 'd';
    form.appendChild(area);
    document.body.appendChild(form);

    this.form = form;
    this.area = area;
  }

  this.form.action = this.uri();

  function complete () {
    initIframe();
    fn();
  }

  function initIframe () {
    if (self.iframe) {
      try {
        self.form.removeChild(self.iframe);
      } catch (e) {
        self.onError('jsonp polling iframe removal error', e);
      }
    }

    try {
      // ie6 dynamic iframes with target="" support (thanks Chris Lambacher)
      var html = '<iframe src="javascript:0" name="' + self.iframeId + '">';
      iframe = document.createElement(html);
    } catch (e) {
      iframe = document.createElement('iframe');
      iframe.name = self.iframeId;
      iframe.src = 'javascript:0';
    }

    iframe.id = self.iframeId;

    self.form.appendChild(iframe);
    self.iframe = iframe;
  }

  initIframe();

  // escape \n to prevent it from being converted into \r\n by some UAs
  // double escaping is required for escaped new lines because unescaping of new lines can be done safely on server-side
  data = data.replace(rEscapedNewline, '\\\n');
  this.area.value = data.replace(rNewline, '\\n');

  try {
    this.form.submit();
  } catch (e) {}

  if (this.iframe.attachEvent) {
    this.iframe.onreadystatechange = function () {
      if (self.iframe.readyState === 'complete') {
        complete();
      }
    };
  } else {
    this.iframe.onload = complete;
  }
};

/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(0)))

/***/ }),
/* 55 */
/***/ (function(module, exports, __webpack_require__) {

/* WEBPACK VAR INJECTION */(function(global) {/**
 * Module requirements.
 */

var XMLHttpRequest = __webpack_require__(11);
var Polling = __webpack_require__(18);
var Emitter = __webpack_require__(1);
var inherit = __webpack_require__(4);
var debug = __webpack_require__(5)('engine.io-client:polling-xhr');

/**
 * Module exports.
 */

module.exports = XHR;
module.exports.Request = Request;

/**
 * Empty function
 */

function empty () {}

/**
 * XHR Polling constructor.
 *
 * @param {Object} opts
 * @api public
 */

function XHR (opts) {
  Polling.call(this, opts);
  this.requestTimeout = opts.requestTimeout;
  this.extraHeaders = opts.extraHeaders;

  if (global.location) {
    var isSSL = 'https:' === location.protocol;
    var port = location.port;

    // some user agents have empty `location.port`
    if (!port) {
      port = isSSL ? 443 : 80;
    }

    this.xd = opts.hostname !== global.location.hostname ||
      port !== opts.port;
    this.xs = opts.secure !== isSSL;
  }
}

/**
 * Inherits from Polling.
 */

inherit(XHR, Polling);

/**
 * XHR supports binary
 */

XHR.prototype.supportsBinary = true;

/**
 * Creates a request.
 *
 * @param {String} method
 * @api private
 */

XHR.prototype.request = function (opts) {
  opts = opts || {};
  opts.uri = this.uri();
  opts.xd = this.xd;
  opts.xs = this.xs;
  opts.agent = this.agent || false;
  opts.supportsBinary = this.supportsBinary;
  opts.enablesXDR = this.enablesXDR;

  // SSL options for Node.js client
  opts.pfx = this.pfx;
  opts.key = this.key;
  opts.passphrase = this.passphrase;
  opts.cert = this.cert;
  opts.ca = this.ca;
  opts.ciphers = this.ciphers;
  opts.rejectUnauthorized = this.rejectUnauthorized;
  opts.requestTimeout = this.requestTimeout;

  // other options for Node.js client
  opts.extraHeaders = this.extraHeaders;

  return new Request(opts);
};

/**
 * Sends data.
 *
 * @param {String} data to send.
 * @param {Function} called upon flush.
 * @api private
 */

XHR.prototype.doWrite = function (data, fn) {
  var isBinary = typeof data !== 'string' && data !== undefined;
  var req = this.request({ method: 'POST', data: data, isBinary: isBinary });
  var self = this;
  req.on('success', fn);
  req.on('error', function (err) {
    self.onError('xhr post error', err);
  });
  this.sendXhr = req;
};

/**
 * Starts a poll cycle.
 *
 * @api private
 */

XHR.prototype.doPoll = function () {
  debug('xhr poll');
  var req = this.request();
  var self = this;
  req.on('data', function (data) {
    self.onData(data);
  });
  req.on('error', function (err) {
    self.onError('xhr poll error', err);
  });
  this.pollXhr = req;
};

/**
 * Request constructor
 *
 * @param {Object} options
 * @api public
 */

function Request (opts) {
  this.method = opts.method || 'GET';
  this.uri = opts.uri;
  this.xd = !!opts.xd;
  this.xs = !!opts.xs;
  this.async = false !== opts.async;
  this.data = undefined !== opts.data ? opts.data : null;
  this.agent = opts.agent;
  this.isBinary = opts.isBinary;
  this.supportsBinary = opts.supportsBinary;
  this.enablesXDR = opts.enablesXDR;
  this.requestTimeout = opts.requestTimeout;

  // SSL options for Node.js client
  this.pfx = opts.pfx;
  this.key = opts.key;
  this.passphrase = opts.passphrase;
  this.cert = opts.cert;
  this.ca = opts.ca;
  this.ciphers = opts.ciphers;
  this.rejectUnauthorized = opts.rejectUnauthorized;

  // other options for Node.js client
  this.extraHeaders = opts.extraHeaders;

  this.create();
}

/**
 * Mix in `Emitter`.
 */

Emitter(Request.prototype);

/**
 * Creates the XHR object and sends the request.
 *
 * @api private
 */

Request.prototype.create = function () {
  var opts = { agent: this.agent, xdomain: this.xd, xscheme: this.xs, enablesXDR: this.enablesXDR };

  // SSL options for Node.js client
  opts.pfx = this.pfx;
  opts.key = this.key;
  opts.passphrase = this.passphrase;
  opts.cert = this.cert;
  opts.ca = this.ca;
  opts.ciphers = this.ciphers;
  opts.rejectUnauthorized = this.rejectUnauthorized;

  var xhr = this.xhr = new XMLHttpRequest(opts);
  var self = this;

  try {
    debug('xhr open %s: %s', this.method, this.uri);
    xhr.open(this.method, this.uri, this.async);
    try {
      if (this.extraHeaders) {
        xhr.setDisableHeaderCheck && xhr.setDisableHeaderCheck(true);
        for (var i in this.extraHeaders) {
          if (this.extraHeaders.hasOwnProperty(i)) {
            xhr.setRequestHeader(i, this.extraHeaders[i]);
          }
        }
      }
    } catch (e) {}

    if ('POST' === this.method) {
      try {
        if (this.isBinary) {
          xhr.setRequestHeader('Content-type', 'application/octet-stream');
        } else {
          xhr.setRequestHeader('Content-type', 'text/plain;charset=UTF-8');
        }
      } catch (e) {}
    }

    try {
      xhr.setRequestHeader('Accept', '*/*');
    } catch (e) {}

    // ie6 check
    if ('withCredentials' in xhr) {
      xhr.withCredentials = true;
    }

    if (this.requestTimeout) {
      xhr.timeout = this.requestTimeout;
    }

    if (this.hasXDR()) {
      xhr.onload = function () {
        self.onLoad();
      };
      xhr.onerror = function () {
        self.onError(xhr.responseText);
      };
    } else {
      xhr.onreadystatechange = function () {
        if (xhr.readyState === 2) {
          var contentType;
          try {
            contentType = xhr.getResponseHeader('Content-Type');
          } catch (e) {}
          if (contentType === 'application/octet-stream') {
            xhr.responseType = 'arraybuffer';
          }
        }
        if (4 !== xhr.readyState) return;
        if (200 === xhr.status || 1223 === xhr.status) {
          self.onLoad();
        } else {
          // make sure the `error` event handler that's user-set
          // does not throw in the same tick and gets caught here
          setTimeout(function () {
            self.onError(xhr.status);
          }, 0);
        }
      };
    }

    debug('xhr data %s', this.data);
    xhr.send(this.data);
  } catch (e) {
    // Need to defer since .create() is called directly fhrom the constructor
    // and thus the 'error' event can only be only bound *after* this exception
    // occurs.  Therefore, also, we cannot throw here at all.
    setTimeout(function () {
      self.onError(e);
    }, 0);
    return;
  }

  if (global.document) {
    this.index = Request.requestsCount++;
    Request.requests[this.index] = this;
  }
};

/**
 * Called upon successful response.
 *
 * @api private
 */

Request.prototype.onSuccess = function () {
  this.emit('success');
  this.cleanup();
};

/**
 * Called if we have data.
 *
 * @api private
 */

Request.prototype.onData = function (data) {
  this.emit('data', data);
  this.onSuccess();
};

/**
 * Called upon error.
 *
 * @api private
 */

Request.prototype.onError = function (err) {
  this.emit('error', err);
  this.cleanup(true);
};

/**
 * Cleans up house.
 *
 * @api private
 */

Request.prototype.cleanup = function (fromError) {
  if ('undefined' === typeof this.xhr || null === this.xhr) {
    return;
  }
  // xmlhttprequest
  if (this.hasXDR()) {
    this.xhr.onload = this.xhr.onerror = empty;
  } else {
    this.xhr.onreadystatechange = empty;
  }

  if (fromError) {
    try {
      this.xhr.abort();
    } catch (e) {}
  }

  if (global.document) {
    delete Request.requests[this.index];
  }

  this.xhr = null;
};

/**
 * Called upon load.
 *
 * @api private
 */

Request.prototype.onLoad = function () {
  var data;
  try {
    var contentType;
    try {
      contentType = this.xhr.getResponseHeader('Content-Type');
    } catch (e) {}
    if (contentType === 'application/octet-stream') {
      data = this.xhr.response || this.xhr.responseText;
    } else {
      data = this.xhr.responseText;
    }
  } catch (e) {
    this.onError(e);
  }
  if (null != data) {
    this.onData(data);
  }
};

/**
 * Check if it has XDomainRequest.
 *
 * @api private
 */

Request.prototype.hasXDR = function () {
  return 'undefined' !== typeof global.XDomainRequest && !this.xs && this.enablesXDR;
};

/**
 * Aborts the request.
 *
 * @api public
 */

Request.prototype.abort = function () {
  this.cleanup();
};

/**
 * Aborts pending requests when unloading the window. This is needed to prevent
 * memory leaks (e.g. when using IE) and to ensure that no spurious error is
 * emitted.
 */

Request.requestsCount = 0;
Request.requests = {};

if (global.document) {
  if (global.attachEvent) {
    global.attachEvent('onunload', unloadHandler);
  } else if (global.addEventListener) {
    global.addEventListener('beforeunload', unloadHandler, false);
  }
}

function unloadHandler () {
  for (var i in Request.requests) {
    if (Request.requests.hasOwnProperty(i)) {
      Request.requests[i].abort();
    }
  }
}

/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(0)))

/***/ }),
/* 56 */
/***/ (function(module, exports, __webpack_require__) {

/* WEBPACK VAR INJECTION */(function(global) {/**
 * Module dependencies.
 */

var Transport = __webpack_require__(10);
var parser = __webpack_require__(2);
var parseqs = __webpack_require__(6);
var inherit = __webpack_require__(4);
var yeast = __webpack_require__(27);
var debug = __webpack_require__(5)('engine.io-client:websocket');
var BrowserWebSocket = global.WebSocket || global.MozWebSocket;
var NodeWebSocket;
if (typeof window === 'undefined') {
  try {
    NodeWebSocket = __webpack_require__(71);
  } catch (e) { }
}

/**
 * Get either the `WebSocket` or `MozWebSocket` globals
 * in the browser or try to resolve WebSocket-compatible
 * interface exposed by `ws` for Node-like environment.
 */

var WebSocket = BrowserWebSocket;
if (!WebSocket && typeof window === 'undefined') {
  WebSocket = NodeWebSocket;
}

/**
 * Module exports.
 */

module.exports = WS;

/**
 * WebSocket transport constructor.
 *
 * @api {Object} connection options
 * @api public
 */

function WS (opts) {
  var forceBase64 = (opts && opts.forceBase64);
  if (forceBase64) {
    this.supportsBinary = false;
  }
  this.perMessageDeflate = opts.perMessageDeflate;
  this.usingBrowserWebSocket = BrowserWebSocket && !opts.forceNode;
  this.protocols = opts.protocols;
  if (!this.usingBrowserWebSocket) {
    WebSocket = NodeWebSocket;
  }
  Transport.call(this, opts);
}

/**
 * Inherits from Transport.
 */

inherit(WS, Transport);

/**
 * Transport name.
 *
 * @api public
 */

WS.prototype.name = 'websocket';

/*
 * WebSockets support binary
 */

WS.prototype.supportsBinary = true;

/**
 * Opens socket.
 *
 * @api private
 */

WS.prototype.doOpen = function () {
  if (!this.check()) {
    // let probe timeout
    return;
  }

  var uri = this.uri();
  var protocols = this.protocols;
  var opts = {
    agent: this.agent,
    perMessageDeflate: this.perMessageDeflate
  };

  // SSL options for Node.js client
  opts.pfx = this.pfx;
  opts.key = this.key;
  opts.passphrase = this.passphrase;
  opts.cert = this.cert;
  opts.ca = this.ca;
  opts.ciphers = this.ciphers;
  opts.rejectUnauthorized = this.rejectUnauthorized;
  if (this.extraHeaders) {
    opts.headers = this.extraHeaders;
  }
  if (this.localAddress) {
    opts.localAddress = this.localAddress;
  }

  try {
    this.ws = this.usingBrowserWebSocket ? (protocols ? new WebSocket(uri, protocols) : new WebSocket(uri)) : new WebSocket(uri, protocols, opts);
  } catch (err) {
    return this.emit('error', err);
  }

  if (this.ws.binaryType === undefined) {
    this.supportsBinary = false;
  }

  if (this.ws.supports && this.ws.supports.binary) {
    this.supportsBinary = true;
    this.ws.binaryType = 'nodebuffer';
  } else {
    this.ws.binaryType = 'arraybuffer';
  }

  this.addEventListeners();
};

/**
 * Adds event listeners to the socket
 *
 * @api private
 */

WS.prototype.addEventListeners = function () {
  var self = this;

  this.ws.onopen = function () {
    self.onOpen();
  };
  this.ws.onclose = function () {
    self.onClose();
  };
  this.ws.onmessage = function (ev) {
    self.onData(ev.data);
  };
  this.ws.onerror = function (e) {
    self.onError('websocket error', e);
  };
};

/**
 * Writes data to socket.
 *
 * @param {Array} array of packets.
 * @api private
 */

WS.prototype.write = function (packets) {
  var self = this;
  this.writable = false;

  // encodePacket efficient as it uses WS framing
  // no need for encodePayload
  var total = packets.length;
  for (var i = 0, l = total; i < l; i++) {
    (function (packet) {
      parser.encodePacket(packet, self.supportsBinary, function (data) {
        if (!self.usingBrowserWebSocket) {
          // always create a new object (GH-437)
          var opts = {};
          if (packet.options) {
            opts.compress = packet.options.compress;
          }

          if (self.perMessageDeflate) {
            var len = 'string' === typeof data ? global.Buffer.byteLength(data) : data.length;
            if (len < self.perMessageDeflate.threshold) {
              opts.compress = false;
            }
          }
        }

        // Sometimes the websocket has already been closed but the browser didn't
        // have a chance of informing us about it yet, in that case send will
        // throw an error
        try {
          if (self.usingBrowserWebSocket) {
            // TypeError is thrown when passing the second argument on Safari
            self.ws.send(data);
          } else {
            self.ws.send(data, opts);
          }
        } catch (e) {
          debug('websocket closed before onclose event');
        }

        --total || done();
      });
    })(packets[i]);
  }

  function done () {
    self.emit('flush');

    // fake drain
    // defer to next tick to allow Socket to clear writeBuffer
    setTimeout(function () {
      self.writable = true;
      self.emit('drain');
    }, 0);
  }
};

/**
 * Called upon close
 *
 * @api private
 */

WS.prototype.onClose = function () {
  Transport.prototype.onClose.call(this);
};

/**
 * Closes socket.
 *
 * @api private
 */

WS.prototype.doClose = function () {
  if (typeof this.ws !== 'undefined') {
    this.ws.close();
  }
};

/**
 * Generates uri for connection.
 *
 * @api private
 */

WS.prototype.uri = function () {
  var query = this.query || {};
  var schema = this.secure ? 'wss' : 'ws';
  var port = '';

  // avoid port if default for schema
  if (this.port && (('wss' === schema && Number(this.port) !== 443) ||
    ('ws' === schema && Number(this.port) !== 80))) {
    port = ':' + this.port;
  }

  // append timestamp to URI
  if (this.timestampRequests) {
    query[this.timestampParam] = yeast();
  }

  // communicate binary support capabilities
  if (!this.supportsBinary) {
    query.b64 = 1;
  }

  query = parseqs.encode(query);

  // prepend ? to query
  if (query.length) {
    query = '?' + query;
  }

  var ipv6 = this.hostname.indexOf(':') !== -1;
  return schema + '://' + (ipv6 ? '[' + this.hostname + ']' : this.hostname) + port + this.path + query;
};

/**
 * Feature detection for WebSocket.
 *
 * @return {Boolean} whether this transport is available.
 * @api public
 */

WS.prototype.check = function () {
  return !!WebSocket && !('__initialize' in WebSocket && this.name === WS.prototype.name);
};

/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(0)))

/***/ }),
/* 57 */
/***/ (function(module, exports, __webpack_require__) {


/**
 * This is the common logic for both the Node.js and web browser
 * implementations of `debug()`.
 *
 * Expose `debug()` as the module.
 */

exports = module.exports = createDebug.debug = createDebug['default'] = createDebug;
exports.coerce = coerce;
exports.disable = disable;
exports.enable = enable;
exports.enabled = enabled;
exports.humanize = __webpack_require__(58);

/**
 * The currently active debug mode names, and names to skip.
 */

exports.names = [];
exports.skips = [];

/**
 * Map of special "%n" handling functions, for the debug "format" argument.
 *
 * Valid key names are a single, lower or upper-case letter, i.e. "n" and "N".
 */

exports.formatters = {};

/**
 * Previous log timestamp.
 */

var prevTime;

/**
 * Select a color.
 * @param {String} namespace
 * @return {Number}
 * @api private
 */

function selectColor(namespace) {
  var hash = 0, i;

  for (i in namespace) {
    hash  = ((hash << 5) - hash) + namespace.charCodeAt(i);
    hash |= 0; // Convert to 32bit integer
  }

  return exports.colors[Math.abs(hash) % exports.colors.length];
}

/**
 * Create a debugger with the given `namespace`.
 *
 * @param {String} namespace
 * @return {Function}
 * @api public
 */

function createDebug(namespace) {

  function debug() {
    // disabled?
    if (!debug.enabled) return;

    var self = debug;

    // set `diff` timestamp
    var curr = +new Date();
    var ms = curr - (prevTime || curr);
    self.diff = ms;
    self.prev = prevTime;
    self.curr = curr;
    prevTime = curr;

    // turn the `arguments` into a proper Array
    var args = new Array(arguments.length);
    for (var i = 0; i < args.length; i++) {
      args[i] = arguments[i];
    }

    args[0] = exports.coerce(args[0]);

    if ('string' !== typeof args[0]) {
      // anything else let's inspect with %O
      args.unshift('%O');
    }

    // apply any `formatters` transformations
    var index = 0;
    args[0] = args[0].replace(/%([a-zA-Z%])/g, function(match, format) {
      // if we encounter an escaped % then don't increase the array index
      if (match === '%%') return match;
      index++;
      var formatter = exports.formatters[format];
      if ('function' === typeof formatter) {
        var val = args[index];
        match = formatter.call(self, val);

        // now we need to remove `args[index]` since it's inlined in the `format`
        args.splice(index, 1);
        index--;
      }
      return match;
    });

    // apply env-specific formatting (colors, etc.)
    exports.formatArgs.call(self, args);

    var logFn = debug.log || exports.log || console.log.bind(console);
    logFn.apply(self, args);
  }

  debug.namespace = namespace;
  debug.enabled = exports.enabled(namespace);
  debug.useColors = exports.useColors();
  debug.color = selectColor(namespace);

  // env-specific initialization logic for debug instances
  if ('function' === typeof exports.init) {
    exports.init(debug);
  }

  return debug;
}

/**
 * Enables a debug mode by namespaces. This can include modes
 * separated by a colon and wildcards.
 *
 * @param {String} namespaces
 * @api public
 */

function enable(namespaces) {
  exports.save(namespaces);

  exports.names = [];
  exports.skips = [];

  var split = (typeof namespaces === 'string' ? namespaces : '').split(/[\s,]+/);
  var len = split.length;

  for (var i = 0; i < len; i++) {
    if (!split[i]) continue; // ignore empty strings
    namespaces = split[i].replace(/\*/g, '.*?');
    if (namespaces[0] === '-') {
      exports.skips.push(new RegExp('^' + namespaces.substr(1) + '$'));
    } else {
      exports.names.push(new RegExp('^' + namespaces + '$'));
    }
  }
}

/**
 * Disable debug output.
 *
 * @api public
 */

function disable() {
  exports.enable('');
}

/**
 * Returns true if the given mode name is enabled, false otherwise.
 *
 * @param {String} name
 * @return {Boolean}
 * @api public
 */

function enabled(name) {
  var i, len;
  for (i = 0, len = exports.skips.length; i < len; i++) {
    if (exports.skips[i].test(name)) {
      return false;
    }
  }
  for (i = 0, len = exports.names.length; i < len; i++) {
    if (exports.names[i].test(name)) {
      return true;
    }
  }
  return false;
}

/**
 * Coerce `val`.
 *
 * @param {Mixed} val
 * @return {Mixed}
 * @api private
 */

function coerce(val) {
  if (val instanceof Error) return val.stack || val.message;
  return val;
}


/***/ }),
/* 58 */
/***/ (function(module, exports) {

/**
 * Helpers.
 */

var s = 1000;
var m = s * 60;
var h = m * 60;
var d = h * 24;
var y = d * 365.25;

/**
 * Parse or format the given `val`.
 *
 * Options:
 *
 *  - `long` verbose formatting [false]
 *
 * @param {String|Number} val
 * @param {Object} [options]
 * @throws {Error} throw an error if val is not a non-empty string or a number
 * @return {String|Number}
 * @api public
 */

module.exports = function(val, options) {
  options = options || {};
  var type = typeof val;
  if (type === 'string' && val.length > 0) {
    return parse(val);
  } else if (type === 'number' && isNaN(val) === false) {
    return options.long ? fmtLong(val) : fmtShort(val);
  }
  throw new Error(
    'val is not a non-empty string or a valid number. val=' +
      JSON.stringify(val)
  );
};

/**
 * Parse the given `str` and return milliseconds.
 *
 * @param {String} str
 * @return {Number}
 * @api private
 */

function parse(str) {
  str = String(str);
  if (str.length > 100) {
    return;
  }
  var match = /^((?:\d+)?\.?\d+) *(milliseconds?|msecs?|ms|seconds?|secs?|s|minutes?|mins?|m|hours?|hrs?|h|days?|d|years?|yrs?|y)?$/i.exec(
    str
  );
  if (!match) {
    return;
  }
  var n = parseFloat(match[1]);
  var type = (match[2] || 'ms').toLowerCase();
  switch (type) {
    case 'years':
    case 'year':
    case 'yrs':
    case 'yr':
    case 'y':
      return n * y;
    case 'days':
    case 'day':
    case 'd':
      return n * d;
    case 'hours':
    case 'hour':
    case 'hrs':
    case 'hr':
    case 'h':
      return n * h;
    case 'minutes':
    case 'minute':
    case 'mins':
    case 'min':
    case 'm':
      return n * m;
    case 'seconds':
    case 'second':
    case 'secs':
    case 'sec':
    case 's':
      return n * s;
    case 'milliseconds':
    case 'millisecond':
    case 'msecs':
    case 'msec':
    case 'ms':
      return n;
    default:
      return undefined;
  }
}

/**
 * Short format for `ms`.
 *
 * @param {Number} ms
 * @return {String}
 * @api private
 */

function fmtShort(ms) {
  if (ms >= d) {
    return Math.round(ms / d) + 'd';
  }
  if (ms >= h) {
    return Math.round(ms / h) + 'h';
  }
  if (ms >= m) {
    return Math.round(ms / m) + 'm';
  }
  if (ms >= s) {
    return Math.round(ms / s) + 's';
  }
  return ms + 'ms';
}

/**
 * Long format for `ms`.
 *
 * @param {Number} ms
 * @return {String}
 * @api private
 */

function fmtLong(ms) {
  return plural(ms, d, 'day') ||
    plural(ms, h, 'hour') ||
    plural(ms, m, 'minute') ||
    plural(ms, s, 'second') ||
    ms + ' ms';
}

/**
 * Pluralization helper.
 */

function plural(ms, n, name) {
  if (ms < n) {
    return;
  }
  if (ms < n * 1.5) {
    return Math.floor(ms / n) + ' ' + name;
  }
  return Math.ceil(ms / n) + ' ' + name + 's';
}


/***/ }),
/* 59 */
/***/ (function(module, exports) {


/**
 * Gets the keys for an object.
 *
 * @return {Array} keys
 * @api private
 */

module.exports = Object.keys || function keys (obj){
  var arr = [];
  var has = Object.prototype.hasOwnProperty;

  for (var i in obj) {
    if (has.call(obj, i)) {
      arr.push(i);
    }
  }
  return arr;
};


/***/ }),
/* 60 */
/***/ (function(module, exports, __webpack_require__) {

/* WEBPACK VAR INJECTION */(function(module, global) {var __WEBPACK_AMD_DEFINE_RESULT__;/*! https://mths.be/utf8js v2.1.2 by @mathias */
;(function(root) {

	// Detect free variables `exports`
	var freeExports = typeof exports == 'object' && exports;

	// Detect free variable `module`
	var freeModule = typeof module == 'object' && module &&
		module.exports == freeExports && module;

	// Detect free variable `global`, from Node.js or Browserified code,
	// and use it as `root`
	var freeGlobal = typeof global == 'object' && global;
	if (freeGlobal.global === freeGlobal || freeGlobal.window === freeGlobal) {
		root = freeGlobal;
	}

	/*--------------------------------------------------------------------------*/

	var stringFromCharCode = String.fromCharCode;

	// Taken from https://mths.be/punycode
	function ucs2decode(string) {
		var output = [];
		var counter = 0;
		var length = string.length;
		var value;
		var extra;
		while (counter < length) {
			value = string.charCodeAt(counter++);
			if (value >= 0xD800 && value <= 0xDBFF && counter < length) {
				// high surrogate, and there is a next character
				extra = string.charCodeAt(counter++);
				if ((extra & 0xFC00) == 0xDC00) { // low surrogate
					output.push(((value & 0x3FF) << 10) + (extra & 0x3FF) + 0x10000);
				} else {
					// unmatched surrogate; only append this code unit, in case the next
					// code unit is the high surrogate of a surrogate pair
					output.push(value);
					counter--;
				}
			} else {
				output.push(value);
			}
		}
		return output;
	}

	// Taken from https://mths.be/punycode
	function ucs2encode(array) {
		var length = array.length;
		var index = -1;
		var value;
		var output = '';
		while (++index < length) {
			value = array[index];
			if (value > 0xFFFF) {
				value -= 0x10000;
				output += stringFromCharCode(value >>> 10 & 0x3FF | 0xD800);
				value = 0xDC00 | value & 0x3FF;
			}
			output += stringFromCharCode(value);
		}
		return output;
	}

	function checkScalarValue(codePoint, strict) {
		if (codePoint >= 0xD800 && codePoint <= 0xDFFF) {
			if (strict) {
				throw Error(
					'Lone surrogate U+' + codePoint.toString(16).toUpperCase() +
					' is not a scalar value'
				);
			}
			return false;
		}
		return true;
	}
	/*--------------------------------------------------------------------------*/

	function createByte(codePoint, shift) {
		return stringFromCharCode(((codePoint >> shift) & 0x3F) | 0x80);
	}

	function encodeCodePoint(codePoint, strict) {
		if ((codePoint & 0xFFFFFF80) == 0) { // 1-byte sequence
			return stringFromCharCode(codePoint);
		}
		var symbol = '';
		if ((codePoint & 0xFFFFF800) == 0) { // 2-byte sequence
			symbol = stringFromCharCode(((codePoint >> 6) & 0x1F) | 0xC0);
		}
		else if ((codePoint & 0xFFFF0000) == 0) { // 3-byte sequence
			if (!checkScalarValue(codePoint, strict)) {
				codePoint = 0xFFFD;
			}
			symbol = stringFromCharCode(((codePoint >> 12) & 0x0F) | 0xE0);
			symbol += createByte(codePoint, 6);
		}
		else if ((codePoint & 0xFFE00000) == 0) { // 4-byte sequence
			symbol = stringFromCharCode(((codePoint >> 18) & 0x07) | 0xF0);
			symbol += createByte(codePoint, 12);
			symbol += createByte(codePoint, 6);
		}
		symbol += stringFromCharCode((codePoint & 0x3F) | 0x80);
		return symbol;
	}

	function utf8encode(string, opts) {
		opts = opts || {};
		var strict = false !== opts.strict;

		var codePoints = ucs2decode(string);
		var length = codePoints.length;
		var index = -1;
		var codePoint;
		var byteString = '';
		while (++index < length) {
			codePoint = codePoints[index];
			byteString += encodeCodePoint(codePoint, strict);
		}
		return byteString;
	}

	/*--------------------------------------------------------------------------*/

	function readContinuationByte() {
		if (byteIndex >= byteCount) {
			throw Error('Invalid byte index');
		}

		var continuationByte = byteArray[byteIndex] & 0xFF;
		byteIndex++;

		if ((continuationByte & 0xC0) == 0x80) {
			return continuationByte & 0x3F;
		}

		// If we end up here, it’s not a continuation byte
		throw Error('Invalid continuation byte');
	}

	function decodeSymbol(strict) {
		var byte1;
		var byte2;
		var byte3;
		var byte4;
		var codePoint;

		if (byteIndex > byteCount) {
			throw Error('Invalid byte index');
		}

		if (byteIndex == byteCount) {
			return false;
		}

		// Read first byte
		byte1 = byteArray[byteIndex] & 0xFF;
		byteIndex++;

		// 1-byte sequence (no continuation bytes)
		if ((byte1 & 0x80) == 0) {
			return byte1;
		}

		// 2-byte sequence
		if ((byte1 & 0xE0) == 0xC0) {
			byte2 = readContinuationByte();
			codePoint = ((byte1 & 0x1F) << 6) | byte2;
			if (codePoint >= 0x80) {
				return codePoint;
			} else {
				throw Error('Invalid continuation byte');
			}
		}

		// 3-byte sequence (may include unpaired surrogates)
		if ((byte1 & 0xF0) == 0xE0) {
			byte2 = readContinuationByte();
			byte3 = readContinuationByte();
			codePoint = ((byte1 & 0x0F) << 12) | (byte2 << 6) | byte3;
			if (codePoint >= 0x0800) {
				return checkScalarValue(codePoint, strict) ? codePoint : 0xFFFD;
			} else {
				throw Error('Invalid continuation byte');
			}
		}

		// 4-byte sequence
		if ((byte1 & 0xF8) == 0xF0) {
			byte2 = readContinuationByte();
			byte3 = readContinuationByte();
			byte4 = readContinuationByte();
			codePoint = ((byte1 & 0x07) << 0x12) | (byte2 << 0x0C) |
				(byte3 << 0x06) | byte4;
			if (codePoint >= 0x010000 && codePoint <= 0x10FFFF) {
				return codePoint;
			}
		}

		throw Error('Invalid UTF-8 detected');
	}

	var byteArray;
	var byteCount;
	var byteIndex;
	function utf8decode(byteString, opts) {
		opts = opts || {};
		var strict = false !== opts.strict;

		byteArray = ucs2decode(byteString);
		byteCount = byteArray.length;
		byteIndex = 0;
		var codePoints = [];
		var tmp;
		while ((tmp = decodeSymbol(strict)) !== false) {
			codePoints.push(tmp);
		}
		return ucs2encode(codePoints);
	}

	/*--------------------------------------------------------------------------*/

	var utf8 = {
		'version': '2.1.2',
		'encode': utf8encode,
		'decode': utf8decode
	};

	// Some AMD build optimizers, like r.js, check for specific condition patterns
	// like the following:
	if (
		true
	) {
		!(__WEBPACK_AMD_DEFINE_RESULT__ = function() {
			return utf8;
		}.call(exports, __webpack_require__, exports, module),
				__WEBPACK_AMD_DEFINE_RESULT__ !== undefined && (module.exports = __WEBPACK_AMD_DEFINE_RESULT__));
	}	else if (freeExports && !freeExports.nodeType) {
		if (freeModule) { // in Node.js or RingoJS v0.8.0+
			freeModule.exports = utf8;
		} else { // in Narwhal or RingoJS v0.7.0-
			var object = {};
			var hasOwnProperty = object.hasOwnProperty;
			for (var key in utf8) {
				hasOwnProperty.call(utf8, key) && (freeExports[key] = utf8[key]);
			}
		}
	} else { // in Rhino or a web browser
		root.utf8 = utf8;
	}

}(this));

/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(42)(module), __webpack_require__(0)))

/***/ }),
/* 61 */
/***/ (function(module, exports) {


/**
 * Module exports.
 *
 * Logic borrowed from Modernizr:
 *
 *   - https://github.com/Modernizr/Modernizr/blob/master/feature-detects/cors.js
 */

try {
  module.exports = typeof XMLHttpRequest !== 'undefined' &&
    'withCredentials' in new XMLHttpRequest();
} catch (err) {
  // if XMLHttp support is disabled in IE then it will throw
  // when trying to create
  module.exports = false;
}


/***/ }),
/* 62 */
/***/ (function(module, exports, __webpack_require__) {

/* WEBPACK VAR INJECTION */(function(global) {/**
 * JSON parse.
 *
 * @see Based on jQuery#parseJSON (MIT) and JSON2
 * @api private
 */

var rvalidchars = /^[\],:{}\s]*$/;
var rvalidescape = /\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g;
var rvalidtokens = /"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g;
var rvalidbraces = /(?:^|:|,)(?:\s*\[)+/g;
var rtrimLeft = /^\s+/;
var rtrimRight = /\s+$/;

module.exports = function parsejson(data) {
  if ('string' != typeof data || !data) {
    return null;
  }

  data = data.replace(rtrimLeft, '').replace(rtrimRight, '');

  // Attempt to parse using the native JSON parser first
  if (global.JSON && JSON.parse) {
    return JSON.parse(data);
  }

  if (rvalidchars.test(data.replace(rvalidescape, '@')
      .replace(rvalidtokens, ']')
      .replace(rvalidbraces, ''))) {
    return (new Function('return ' + data))();
  }
};
/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(0)))

/***/ }),
/* 63 */
/***/ (function(module, exports, __webpack_require__) {

/* WEBPACK VAR INJECTION */(function(global) {
/**
 * Module dependencies.
 */

var parseuri = __webpack_require__(22);
var debug = __webpack_require__(7)('socket.io-client:url');

/**
 * Module exports.
 */

module.exports = url;

/**
 * URL parser.
 *
 * @param {String} url
 * @param {Object} An object meant to mimic window.location.
 *                 Defaults to window.location.
 * @api public
 */

function url (uri, loc) {
  var obj = uri;

  // default to window.location
  loc = loc || global.location;
  if (null == uri) uri = loc.protocol + '//' + loc.host;

  // relative path support
  if ('string' === typeof uri) {
    if ('/' === uri.charAt(0)) {
      if ('/' === uri.charAt(1)) {
        uri = loc.protocol + uri;
      } else {
        uri = loc.host + uri;
      }
    }

    if (!/^(https?|wss?):\/\//.test(uri)) {
      debug('protocol-less url %s', uri);
      if ('undefined' !== typeof loc) {
        uri = loc.protocol + '//' + uri;
      } else {
        uri = 'https://' + uri;
      }
    }

    // parse
    debug('parse %s', uri);
    obj = parseuri(uri);
  }

  // make sure we treat `localhost:80` and `localhost` equally
  if (!obj.port) {
    if (/^(http|ws)$/.test(obj.protocol)) {
      obj.port = '80';
    } else if (/^(http|ws)s$/.test(obj.protocol)) {
      obj.port = '443';
    }
  }

  obj.path = obj.path || '/';

  var ipv6 = obj.host.indexOf(':') !== -1;
  var host = ipv6 ? '[' + obj.host + ']' : obj.host;

  // define unique id
  obj.id = obj.protocol + '://' + host + ':' + obj.port;
  // define href
  obj.href = obj.protocol + '://' + host + (loc && loc.port === obj.port ? '' : (':' + obj.port));

  return obj;
}

/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(0)))

/***/ }),
/* 64 */
/***/ (function(module, exports, __webpack_require__) {


/**
 * This is the common logic for both the Node.js and web browser
 * implementations of `debug()`.
 *
 * Expose `debug()` as the module.
 */

exports = module.exports = createDebug.debug = createDebug['default'] = createDebug;
exports.coerce = coerce;
exports.disable = disable;
exports.enable = enable;
exports.enabled = enabled;
exports.humanize = __webpack_require__(65);

/**
 * The currently active debug mode names, and names to skip.
 */

exports.names = [];
exports.skips = [];

/**
 * Map of special "%n" handling functions, for the debug "format" argument.
 *
 * Valid key names are a single, lower or upper-case letter, i.e. "n" and "N".
 */

exports.formatters = {};

/**
 * Previous log timestamp.
 */

var prevTime;

/**
 * Select a color.
 * @param {String} namespace
 * @return {Number}
 * @api private
 */

function selectColor(namespace) {
  var hash = 0, i;

  for (i in namespace) {
    hash  = ((hash << 5) - hash) + namespace.charCodeAt(i);
    hash |= 0; // Convert to 32bit integer
  }

  return exports.colors[Math.abs(hash) % exports.colors.length];
}

/**
 * Create a debugger with the given `namespace`.
 *
 * @param {String} namespace
 * @return {Function}
 * @api public
 */

function createDebug(namespace) {

  function debug() {
    // disabled?
    if (!debug.enabled) return;

    var self = debug;

    // set `diff` timestamp
    var curr = +new Date();
    var ms = curr - (prevTime || curr);
    self.diff = ms;
    self.prev = prevTime;
    self.curr = curr;
    prevTime = curr;

    // turn the `arguments` into a proper Array
    var args = new Array(arguments.length);
    for (var i = 0; i < args.length; i++) {
      args[i] = arguments[i];
    }

    args[0] = exports.coerce(args[0]);

    if ('string' !== typeof args[0]) {
      // anything else let's inspect with %O
      args.unshift('%O');
    }

    // apply any `formatters` transformations
    var index = 0;
    args[0] = args[0].replace(/%([a-zA-Z%])/g, function(match, format) {
      // if we encounter an escaped % then don't increase the array index
      if (match === '%%') return match;
      index++;
      var formatter = exports.formatters[format];
      if ('function' === typeof formatter) {
        var val = args[index];
        match = formatter.call(self, val);

        // now we need to remove `args[index]` since it's inlined in the `format`
        args.splice(index, 1);
        index--;
      }
      return match;
    });

    // apply env-specific formatting (colors, etc.)
    exports.formatArgs.call(self, args);

    var logFn = debug.log || exports.log || console.log.bind(console);
    logFn.apply(self, args);
  }

  debug.namespace = namespace;
  debug.enabled = exports.enabled(namespace);
  debug.useColors = exports.useColors();
  debug.color = selectColor(namespace);

  // env-specific initialization logic for debug instances
  if ('function' === typeof exports.init) {
    exports.init(debug);
  }

  return debug;
}

/**
 * Enables a debug mode by namespaces. This can include modes
 * separated by a colon and wildcards.
 *
 * @param {String} namespaces
 * @api public
 */

function enable(namespaces) {
  exports.save(namespaces);

  exports.names = [];
  exports.skips = [];

  var split = (typeof namespaces === 'string' ? namespaces : '').split(/[\s,]+/);
  var len = split.length;

  for (var i = 0; i < len; i++) {
    if (!split[i]) continue; // ignore empty strings
    namespaces = split[i].replace(/\*/g, '.*?');
    if (namespaces[0] === '-') {
      exports.skips.push(new RegExp('^' + namespaces.substr(1) + '$'));
    } else {
      exports.names.push(new RegExp('^' + namespaces + '$'));
    }
  }
}

/**
 * Disable debug output.
 *
 * @api public
 */

function disable() {
  exports.enable('');
}

/**
 * Returns true if the given mode name is enabled, false otherwise.
 *
 * @param {String} name
 * @return {Boolean}
 * @api public
 */

function enabled(name) {
  var i, len;
  for (i = 0, len = exports.skips.length; i < len; i++) {
    if (exports.skips[i].test(name)) {
      return false;
    }
  }
  for (i = 0, len = exports.names.length; i < len; i++) {
    if (exports.names[i].test(name)) {
      return true;
    }
  }
  return false;
}

/**
 * Coerce `val`.
 *
 * @param {Mixed} val
 * @return {Mixed}
 * @api private
 */

function coerce(val) {
  if (val instanceof Error) return val.stack || val.message;
  return val;
}


/***/ }),
/* 65 */
/***/ (function(module, exports) {

/**
 * Helpers.
 */

var s = 1000;
var m = s * 60;
var h = m * 60;
var d = h * 24;
var y = d * 365.25;

/**
 * Parse or format the given `val`.
 *
 * Options:
 *
 *  - `long` verbose formatting [false]
 *
 * @param {String|Number} val
 * @param {Object} [options]
 * @throws {Error} throw an error if val is not a non-empty string or a number
 * @return {String|Number}
 * @api public
 */

module.exports = function(val, options) {
  options = options || {};
  var type = typeof val;
  if (type === 'string' && val.length > 0) {
    return parse(val);
  } else if (type === 'number' && isNaN(val) === false) {
    return options.long ? fmtLong(val) : fmtShort(val);
  }
  throw new Error(
    'val is not a non-empty string or a valid number. val=' +
      JSON.stringify(val)
  );
};

/**
 * Parse the given `str` and return milliseconds.
 *
 * @param {String} str
 * @return {Number}
 * @api private
 */

function parse(str) {
  str = String(str);
  if (str.length > 100) {
    return;
  }
  var match = /^((?:\d+)?\.?\d+) *(milliseconds?|msecs?|ms|seconds?|secs?|s|minutes?|mins?|m|hours?|hrs?|h|days?|d|years?|yrs?|y)?$/i.exec(
    str
  );
  if (!match) {
    return;
  }
  var n = parseFloat(match[1]);
  var type = (match[2] || 'ms').toLowerCase();
  switch (type) {
    case 'years':
    case 'year':
    case 'yrs':
    case 'yr':
    case 'y':
      return n * y;
    case 'days':
    case 'day':
    case 'd':
      return n * d;
    case 'hours':
    case 'hour':
    case 'hrs':
    case 'hr':
    case 'h':
      return n * h;
    case 'minutes':
    case 'minute':
    case 'mins':
    case 'min':
    case 'm':
      return n * m;
    case 'seconds':
    case 'second':
    case 'secs':
    case 'sec':
    case 's':
      return n * s;
    case 'milliseconds':
    case 'millisecond':
    case 'msecs':
    case 'msec':
    case 'ms':
      return n;
    default:
      return undefined;
  }
}

/**
 * Short format for `ms`.
 *
 * @param {Number} ms
 * @return {String}
 * @api private
 */

function fmtShort(ms) {
  if (ms >= d) {
    return Math.round(ms / d) + 'd';
  }
  if (ms >= h) {
    return Math.round(ms / h) + 'h';
  }
  if (ms >= m) {
    return Math.round(ms / m) + 'm';
  }
  if (ms >= s) {
    return Math.round(ms / s) + 's';
  }
  return ms + 'ms';
}

/**
 * Long format for `ms`.
 *
 * @param {Number} ms
 * @return {String}
 * @api private
 */

function fmtLong(ms) {
  return plural(ms, d, 'day') ||
    plural(ms, h, 'hour') ||
    plural(ms, m, 'minute') ||
    plural(ms, s, 'second') ||
    ms + ' ms';
}

/**
 * Pluralization helper.
 */

function plural(ms, n, name) {
  if (ms < n) {
    return;
  }
  if (ms < n * 1.5) {
    return Math.floor(ms / n) + ' ' + name;
  }
  return Math.ceil(ms / n) + ' ' + name + 's';
}


/***/ }),
/* 66 */
/***/ (function(module, exports, __webpack_require__) {

/* WEBPACK VAR INJECTION */(function(global) {/*global Blob,File*/

/**
 * Module requirements
 */

var isArray = __webpack_require__(21);
var isBuf = __webpack_require__(26);
var toString = Object.prototype.toString;
var withNativeBlob = typeof global.Blob === 'function' || toString.call(global.Blob) === '[object BlobConstructor]';
var withNativeFile = typeof global.File === 'function' || toString.call(global.File) === '[object FileConstructor]';

/**
 * Replaces every Buffer | ArrayBuffer in packet with a numbered placeholder.
 * Anything with blobs or files should be fed through removeBlobs before coming
 * here.
 *
 * @param {Object} packet - socket.io event packet
 * @return {Object} with deconstructed packet and list of buffers
 * @api public
 */

exports.deconstructPacket = function(packet) {
  var buffers = [];
  var packetData = packet.data;
  var pack = packet;
  pack.data = _deconstructPacket(packetData, buffers);
  pack.attachments = buffers.length; // number of binary 'attachments'
  return {packet: pack, buffers: buffers};
};

function _deconstructPacket(data, buffers) {
  if (!data) return data;

  if (isBuf(data)) {
    var placeholder = { _placeholder: true, num: buffers.length };
    buffers.push(data);
    return placeholder;
  } else if (isArray(data)) {
    var newData = new Array(data.length);
    for (var i = 0; i < data.length; i++) {
      newData[i] = _deconstructPacket(data[i], buffers);
    }
    return newData;
  } else if (typeof data === 'object' && !(data instanceof Date)) {
    var newData = {};
    for (var key in data) {
      newData[key] = _deconstructPacket(data[key], buffers);
    }
    return newData;
  }
  return data;
}

/**
 * Reconstructs a binary packet from its placeholder packet and buffers
 *
 * @param {Object} packet - event packet with placeholders
 * @param {Array} buffers - binary buffers to put in placeholder positions
 * @return {Object} reconstructed packet
 * @api public
 */

exports.reconstructPacket = function(packet, buffers) {
  packet.data = _reconstructPacket(packet.data, buffers);
  packet.attachments = undefined; // no longer useful
  return packet;
};

function _reconstructPacket(data, buffers) {
  if (!data) return data;

  if (data && data._placeholder) {
    return buffers[data.num]; // appropriate buffer (should be natural order anyway)
  } else if (isArray(data)) {
    for (var i = 0; i < data.length; i++) {
      data[i] = _reconstructPacket(data[i], buffers);
    }
  } else if (typeof data === 'object') {
    for (var key in data) {
      data[key] = _reconstructPacket(data[key], buffers);
    }
  }

  return data;
}

/**
 * Asynchronously removes Blobs or Files from data via
 * FileReader's readAsArrayBuffer method. Used before encoding
 * data as msgpack. Calls callback with the blobless data.
 *
 * @param {Object} data
 * @param {Function} callback
 * @api private
 */

exports.removeBlobs = function(data, callback) {
  function _removeBlobs(obj, curKey, containingObject) {
    if (!obj) return obj;

    // convert any blob
    if ((withNativeBlob && obj instanceof Blob) ||
        (withNativeFile && obj instanceof File)) {
      pendingBlobs++;

      // async filereader
      var fileReader = new FileReader();
      fileReader.onload = function() { // this.result == arraybuffer
        if (containingObject) {
          containingObject[curKey] = this.result;
        }
        else {
          bloblessData = this.result;
        }

        // if nothing pending its callback time
        if(! --pendingBlobs) {
          callback(bloblessData);
        }
      };

      fileReader.readAsArrayBuffer(obj); // blob -> arraybuffer
    } else if (isArray(obj)) { // handle array
      for (var i = 0; i < obj.length; i++) {
        _removeBlobs(obj[i], i, obj);
      }
    } else if (typeof obj === 'object' && !isBuf(obj)) { // and object
      for (var key in obj) {
        _removeBlobs(obj[key], key, obj);
      }
    }
  }

  var pendingBlobs = 0;
  var bloblessData = data;
  _removeBlobs(bloblessData);
  if (!pendingBlobs) {
    callback(bloblessData);
  }
};

/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(0)))

/***/ }),
/* 67 */
/***/ (function(module, exports, __webpack_require__) {

/* WEBPACK VAR INJECTION */(function(process) {/**
 * This is the web browser implementation of `debug()`.
 *
 * Expose `debug()` as the module.
 */

exports = module.exports = __webpack_require__(68);
exports.log = log;
exports.formatArgs = formatArgs;
exports.save = save;
exports.load = load;
exports.useColors = useColors;
exports.storage = 'undefined' != typeof chrome
               && 'undefined' != typeof chrome.storage
                  ? chrome.storage.local
                  : localstorage();

/**
 * Colors.
 */

exports.colors = [
  'lightseagreen',
  'forestgreen',
  'goldenrod',
  'dodgerblue',
  'darkorchid',
  'crimson'
];

/**
 * Currently only WebKit-based Web Inspectors, Firefox >= v31,
 * and the Firebug extension (any Firefox version) are known
 * to support "%c" CSS customizations.
 *
 * TODO: add a `localStorage` variable to explicitly enable/disable colors
 */

function useColors() {
  // NB: In an Electron preload script, document will be defined but not fully
  // initialized. Since we know we're in Chrome, we'll just detect this case
  // explicitly
  if (typeof window !== 'undefined' && window.process && window.process.type === 'renderer') {
    return true;
  }

  // is webkit? http://stackoverflow.com/a/16459606/376773
  // document is undefined in react-native: https://github.com/facebook/react-native/pull/1632
  return (typeof document !== 'undefined' && document.documentElement && document.documentElement.style && document.documentElement.style.WebkitAppearance) ||
    // is firebug? http://stackoverflow.com/a/398120/376773
    (typeof window !== 'undefined' && window.console && (window.console.firebug || (window.console.exception && window.console.table))) ||
    // is firefox >= v31?
    // https://developer.mozilla.org/en-US/docs/Tools/Web_Console#Styling_messages
    (typeof navigator !== 'undefined' && navigator.userAgent && navigator.userAgent.toLowerCase().match(/firefox\/(\d+)/) && parseInt(RegExp.$1, 10) >= 31) ||
    // double check webkit in userAgent just in case we are in a worker
    (typeof navigator !== 'undefined' && navigator.userAgent && navigator.userAgent.toLowerCase().match(/applewebkit\/(\d+)/));
}

/**
 * Map %j to `JSON.stringify()`, since no Web Inspectors do that by default.
 */

exports.formatters.j = function(v) {
  try {
    return JSON.stringify(v);
  } catch (err) {
    return '[UnexpectedJSONParseError]: ' + err.message;
  }
};


/**
 * Colorize log arguments if enabled.
 *
 * @api public
 */

function formatArgs(args) {
  var useColors = this.useColors;

  args[0] = (useColors ? '%c' : '')
    + this.namespace
    + (useColors ? ' %c' : ' ')
    + args[0]
    + (useColors ? '%c ' : ' ')
    + '+' + exports.humanize(this.diff);

  if (!useColors) return;

  var c = 'color: ' + this.color;
  args.splice(1, 0, c, 'color: inherit')

  // the final "%c" is somewhat tricky, because there could be other
  // arguments passed either before or after the %c, so we need to
  // figure out the correct index to insert the CSS into
  var index = 0;
  var lastC = 0;
  args[0].replace(/%[a-zA-Z%]/g, function(match) {
    if ('%%' === match) return;
    index++;
    if ('%c' === match) {
      // we only are interested in the *last* %c
      // (the user may have provided their own)
      lastC = index;
    }
  });

  args.splice(lastC, 0, c);
}

/**
 * Invokes `console.log()` when available.
 * No-op when `console.log` is not a "function".
 *
 * @api public
 */

function log() {
  // this hackery is required for IE8/9, where
  // the `console.log` function doesn't have 'apply'
  return 'object' === typeof console
    && console.log
    && Function.prototype.apply.call(console.log, console, arguments);
}

/**
 * Save `namespaces`.
 *
 * @param {String} namespaces
 * @api private
 */

function save(namespaces) {
  try {
    if (null == namespaces) {
      exports.storage.removeItem('debug');
    } else {
      exports.storage.debug = namespaces;
    }
  } catch(e) {}
}

/**
 * Load `namespaces`.
 *
 * @return {String} returns the previously persisted debug modes
 * @api private
 */

function load() {
  var r;
  try {
    r = exports.storage.debug;
  } catch(e) {}

  // If debug isn't set in LS, and we're in Electron, try to load $DEBUG
  if (!r && typeof process !== 'undefined' && 'env' in process) {
    r = process.env.DEBUG;
  }

  return r;
}

/**
 * Enable namespaces listed in `localStorage.debug` initially.
 */

exports.enable(load());

/**
 * Localstorage attempts to return the localstorage.
 *
 * This is necessary because safari throws
 * when a user disables cookies/localstorage
 * and you attempt to access it.
 *
 * @return {LocalStorage}
 * @api private
 */

function localstorage() {
  try {
    return window.localStorage;
  } catch (e) {}
}

/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(8)))

/***/ }),
/* 68 */
/***/ (function(module, exports, __webpack_require__) {


/**
 * This is the common logic for both the Node.js and web browser
 * implementations of `debug()`.
 *
 * Expose `debug()` as the module.
 */

exports = module.exports = createDebug.debug = createDebug['default'] = createDebug;
exports.coerce = coerce;
exports.disable = disable;
exports.enable = enable;
exports.enabled = enabled;
exports.humanize = __webpack_require__(69);

/**
 * The currently active debug mode names, and names to skip.
 */

exports.names = [];
exports.skips = [];

/**
 * Map of special "%n" handling functions, for the debug "format" argument.
 *
 * Valid key names are a single, lower or upper-case letter, i.e. "n" and "N".
 */

exports.formatters = {};

/**
 * Previous log timestamp.
 */

var prevTime;

/**
 * Select a color.
 * @param {String} namespace
 * @return {Number}
 * @api private
 */

function selectColor(namespace) {
  var hash = 0, i;

  for (i in namespace) {
    hash  = ((hash << 5) - hash) + namespace.charCodeAt(i);
    hash |= 0; // Convert to 32bit integer
  }

  return exports.colors[Math.abs(hash) % exports.colors.length];
}

/**
 * Create a debugger with the given `namespace`.
 *
 * @param {String} namespace
 * @return {Function}
 * @api public
 */

function createDebug(namespace) {

  function debug() {
    // disabled?
    if (!debug.enabled) return;

    var self = debug;

    // set `diff` timestamp
    var curr = +new Date();
    var ms = curr - (prevTime || curr);
    self.diff = ms;
    self.prev = prevTime;
    self.curr = curr;
    prevTime = curr;

    // turn the `arguments` into a proper Array
    var args = new Array(arguments.length);
    for (var i = 0; i < args.length; i++) {
      args[i] = arguments[i];
    }

    args[0] = exports.coerce(args[0]);

    if ('string' !== typeof args[0]) {
      // anything else let's inspect with %O
      args.unshift('%O');
    }

    // apply any `formatters` transformations
    var index = 0;
    args[0] = args[0].replace(/%([a-zA-Z%])/g, function(match, format) {
      // if we encounter an escaped % then don't increase the array index
      if (match === '%%') return match;
      index++;
      var formatter = exports.formatters[format];
      if ('function' === typeof formatter) {
        var val = args[index];
        match = formatter.call(self, val);

        // now we need to remove `args[index]` since it's inlined in the `format`
        args.splice(index, 1);
        index--;
      }
      return match;
    });

    // apply env-specific formatting (colors, etc.)
    exports.formatArgs.call(self, args);

    var logFn = debug.log || exports.log || console.log.bind(console);
    logFn.apply(self, args);
  }

  debug.namespace = namespace;
  debug.enabled = exports.enabled(namespace);
  debug.useColors = exports.useColors();
  debug.color = selectColor(namespace);

  // env-specific initialization logic for debug instances
  if ('function' === typeof exports.init) {
    exports.init(debug);
  }

  return debug;
}

/**
 * Enables a debug mode by namespaces. This can include modes
 * separated by a colon and wildcards.
 *
 * @param {String} namespaces
 * @api public
 */

function enable(namespaces) {
  exports.save(namespaces);

  exports.names = [];
  exports.skips = [];

  var split = (typeof namespaces === 'string' ? namespaces : '').split(/[\s,]+/);
  var len = split.length;

  for (var i = 0; i < len; i++) {
    if (!split[i]) continue; // ignore empty strings
    namespaces = split[i].replace(/\*/g, '.*?');
    if (namespaces[0] === '-') {
      exports.skips.push(new RegExp('^' + namespaces.substr(1) + '$'));
    } else {
      exports.names.push(new RegExp('^' + namespaces + '$'));
    }
  }
}

/**
 * Disable debug output.
 *
 * @api public
 */

function disable() {
  exports.enable('');
}

/**
 * Returns true if the given mode name is enabled, false otherwise.
 *
 * @param {String} name
 * @return {Boolean}
 * @api public
 */

function enabled(name) {
  var i, len;
  for (i = 0, len = exports.skips.length; i < len; i++) {
    if (exports.skips[i].test(name)) {
      return false;
    }
  }
  for (i = 0, len = exports.names.length; i < len; i++) {
    if (exports.names[i].test(name)) {
      return true;
    }
  }
  return false;
}

/**
 * Coerce `val`.
 *
 * @param {Mixed} val
 * @return {Mixed}
 * @api private
 */

function coerce(val) {
  if (val instanceof Error) return val.stack || val.message;
  return val;
}


/***/ }),
/* 69 */
/***/ (function(module, exports) {

/**
 * Helpers.
 */

var s = 1000;
var m = s * 60;
var h = m * 60;
var d = h * 24;
var y = d * 365.25;

/**
 * Parse or format the given `val`.
 *
 * Options:
 *
 *  - `long` verbose formatting [false]
 *
 * @param {String|Number} val
 * @param {Object} [options]
 * @throws {Error} throw an error if val is not a non-empty string or a number
 * @return {String|Number}
 * @api public
 */

module.exports = function(val, options) {
  options = options || {};
  var type = typeof val;
  if (type === 'string' && val.length > 0) {
    return parse(val);
  } else if (type === 'number' && isNaN(val) === false) {
    return options.long ? fmtLong(val) : fmtShort(val);
  }
  throw new Error(
    'val is not a non-empty string or a valid number. val=' +
      JSON.stringify(val)
  );
};

/**
 * Parse the given `str` and return milliseconds.
 *
 * @param {String} str
 * @return {Number}
 * @api private
 */

function parse(str) {
  str = String(str);
  if (str.length > 100) {
    return;
  }
  var match = /^((?:\d+)?\.?\d+) *(milliseconds?|msecs?|ms|seconds?|secs?|s|minutes?|mins?|m|hours?|hrs?|h|days?|d|years?|yrs?|y)?$/i.exec(
    str
  );
  if (!match) {
    return;
  }
  var n = parseFloat(match[1]);
  var type = (match[2] || 'ms').toLowerCase();
  switch (type) {
    case 'years':
    case 'year':
    case 'yrs':
    case 'yr':
    case 'y':
      return n * y;
    case 'days':
    case 'day':
    case 'd':
      return n * d;
    case 'hours':
    case 'hour':
    case 'hrs':
    case 'hr':
    case 'h':
      return n * h;
    case 'minutes':
    case 'minute':
    case 'mins':
    case 'min':
    case 'm':
      return n * m;
    case 'seconds':
    case 'second':
    case 'secs':
    case 'sec':
    case 's':
      return n * s;
    case 'milliseconds':
    case 'millisecond':
    case 'msecs':
    case 'msec':
    case 'ms':
      return n;
    default:
      return undefined;
  }
}

/**
 * Short format for `ms`.
 *
 * @param {Number} ms
 * @return {String}
 * @api private
 */

function fmtShort(ms) {
  if (ms >= d) {
    return Math.round(ms / d) + 'd';
  }
  if (ms >= h) {
    return Math.round(ms / h) + 'h';
  }
  if (ms >= m) {
    return Math.round(ms / m) + 'm';
  }
  if (ms >= s) {
    return Math.round(ms / s) + 's';
  }
  return ms + 'ms';
}

/**
 * Long format for `ms`.
 *
 * @param {Number} ms
 * @return {String}
 * @api private
 */

function fmtLong(ms) {
  return plural(ms, d, 'day') ||
    plural(ms, h, 'hour') ||
    plural(ms, m, 'minute') ||
    plural(ms, s, 'second') ||
    ms + ' ms';
}

/**
 * Pluralization helper.
 */

function plural(ms, n, name) {
  if (ms < n) {
    return;
  }
  if (ms < n * 1.5) {
    return Math.floor(ms / n) + ' ' + name;
  }
  return Math.ceil(ms / n) + ' ' + name + 's';
}


/***/ }),
/* 70 */
/***/ (function(module, exports) {

module.exports = toArray

function toArray(list, index) {
    var array = []

    index = index || 0

    for (var i = index || 0; i < list.length; i++) {
        array[i - index] = list[i]
    }

    return array
}


/***/ }),
/* 71 */
/***/ (function(module, exports) {

/* (ignored) */

/***/ })
/******/ ]);