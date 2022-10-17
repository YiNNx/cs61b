public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    private static double G = 6.67e-11f;

    public Planet(double xP, double yP, double xV,
            double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        return Math.pow(Math.pow(this.xxPos - p.xxPos, 2) + Math.pow(this.yyPos - p.yyPos, 2), 0.5);
    }

    public double calcForceExertedBy(Planet p) {
        return G * this.mass * p.mass / Math.pow(this.calcDistance(p), 2);
    }

    public double calcForceExertedByX(Planet p) {
        return (this.calcForceExertedBy(p) / this.calcDistance(p)) * (p.xxPos - this.xxPos);
    }

    public double calcForceExertedByY(Planet p) {
        return (this.calcForceExertedBy(p) / this.calcDistance(p)) * (p.yyPos - this.yyPos);
    }

    public double calcNetForceExertedByX(Planet[] planets) {
        double netForceX = 0;
        for (Planet p : planets) {
            if (this.equals(p)) {
                continue;
            }
            netForceX = netForceX + this.calcForceExertedByX(p);
        }
        return netForceX;
    }

    public double calcNetForceExertedByY(Planet[] planets) {
        double netForceY = 0;
        for (Planet p : planets) {
            if (this.equals(p)) {
                continue;
            }
            netForceY = netForceY + this.calcForceExertedByY(p);
        }
        return netForceY;
    }

    public void update(double dt, double xForce, double yForce) {
        double xAcceleration = xForce / this.mass;
        double yAcceleration = yForce / this.mass;

        this.xxVel = this.xxVel + xAcceleration * dt;
        this.yyVel = this.yyVel + yAcceleration * dt;

        this.xxPos = this.xxPos + xxVel * dt;
        this.yyPos = this.yyPos + yyVel * dt;
    }

    public void draw() {
        StdDraw.picture(this.xxPos, this.yyPos, String.format("images/%s", this.imgFileName));
    }
}