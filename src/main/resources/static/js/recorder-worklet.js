class RecorderProcessor extends AudioWorkletProcessor {
    constructor() {
        super();
        this._buffer = [];
        this._frameSize = 2048;
    }

    process(inputs) {
        const input = inputs[0];
        if (input.length > 0) {
            const channel = input[0];
            this._buffer.push(...channel);
            while (this._buffer.length >= this._frameSize) {
                const chunk = this._buffer.slice(0, this._frameSize);
                this.port.postMessage(new Float32Array(chunk)); // ← ここ！
                this._buffer = this._buffer.slice(this._frameSize);
            }
        }
        return true;
    }
}

registerProcessor('recorder-processor', RecorderProcessor);